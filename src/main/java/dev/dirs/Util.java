package dev.dirs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Locale;

final class Util {

  private Util() {
    throw new Error();
  }


  static final String operatingSystemName = System.getProperty("os.name");
  static final char operatingSystem;
  static final char LIN = 'l';
  static final char MAC = 'm';
  static final char WIN = 'w';
  static final char BSD = 'b';
  static final char SOLARIS = 's';

  static final String UTF8_BOM = "\ufeff";

  static {
    final String os = operatingSystemName.toLowerCase(Locale.ROOT);
    if (os.contains("linux"))
      operatingSystem = LIN;
    else if (os.contains("mac"))
      operatingSystem = MAC;
    else if (os.contains("windows"))
      operatingSystem = WIN;
    else if (os.contains("bsd"))
      operatingSystem = BSD;
    else if (os.contains("sunos"))
      operatingSystem = SOLARIS;
    else
      throw new UnsupportedOperatingSystemException("directories are not supported on " + operatingSystemName);
  }

  private static Object base64Encoder = null;
  private static Method base64EncodeMethod = null;
  // This string needs to end up being a multiple of 3 bytes after conversion to UTF-16. (It is currently 1200 bytes.)
  // This is because Base64 converts 3 bytes to 4 letters; other numbers of bytes would introduce padding, which
  // would make it harder to simply concatenate this precomputed string with whatever directories the user requests.
  static final String SCRIPT_START_BASE64 = operatingSystem == 'w' ? toUTF16LEBase64("& {\n" +
      "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8\n" +
      "Add-Type @\"\n" +
      "using System;\n" +
      "using System.Runtime.InteropServices;\n" +
      "public class Dir {\n" +
      "  [DllImport(\"shell32.dll\")]\n" +
      "  private static extern int SHGetKnownFolderPath([MarshalAs(UnmanagedType.LPStruct)] Guid rfid, uint dwFlags, IntPtr hToken, out IntPtr pszPath);\n" +
      "  public static string GetKnownFolderPath(string rfid) {\n" +
      "    IntPtr pszPath;\n" +
      "    if (SHGetKnownFolderPath(new Guid(rfid), 0, IntPtr.Zero, out pszPath) != 0) return \"\";\n" +
      "    string path = Marshal.PtrToStringUni(pszPath);\n" +
      "    Marshal.FreeCoTaskMem(pszPath);\n" +
      "    return path;\n" +
      "  }\n" +
      "}\n" +
      "\"@\n") : null;

  static void requireNonNull(Object value) {
    if (value == null)
      throw new NullPointerException();
  }

  static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  static String defaultIfNullOrEmpty(String value, String fallbackValue, String fallbackArg) {
    requireNonNull(fallbackArg);
    if (isNullOrEmpty(value))
      return ensureSingleSlash(fallbackValue, fallbackArg);
    else
      return value;
  }

  static String defaultIfNullOrEmptyExtended(String value, String valueArg, String fallbackValue, String fallbackArg) {
    requireNonNull(valueArg);
    requireNonNull(fallbackValue);
    requireNonNull(fallbackArg);
    if (isNullOrEmpty(value))
      return ensureSingleSlash(fallbackValue, fallbackArg);
    else
      return ensureSingleSlash(value, valueArg);
  }

  static String ensureSingleSlash(String arg1, String arg2) {
    boolean arg1Slash = arg1.endsWith("/");
    boolean slashArg2 = arg2.startsWith("/");
    if (arg1Slash && slashArg2) {
      StringBuilder buf = new StringBuilder(arg1.length() + arg2.length() - 1);
      buf.append(arg1, 0, arg1.length() - 1).append(arg2);
      return buf.toString();
    } else if (!arg1Slash && !slashArg2) {
      return arg1 + '/' + arg2;
    } else {
      return arg1 + arg2;
    }
  }

  static String linuxRuntimeDir(String path) {
    String runDir = System.getenv("XDG_RUNTIME_DIR");
    if (isNullOrEmpty(runDir))
      return null;
    else if (path == null)
      return runDir;
    else
      return runDir + '/' + path;
  }

  static String linuxExecutableDir(String homeDir, String dataDir) {
    String binDir = System.getenv("XDG_BIN_HOME");
    if (isNullOrEmpty(binDir))
      return defaultIfNullOrEmptyExtended(dataDir, "/../bin/", homeDir, "/.local/bin/");
    else
      return binDir;
  }

  static String[] getXDGUserDirs(String... dirs) {
    int dirsLength = dirs.length;
    StringBuilder buf = new StringBuilder(dirsLength * 22);
    String[] commands = new String[3];
    commands[0] = "/bin/sh";
    commands[1] = "-c";
    for (int i = 0; i < dirsLength; i++) {
      buf.append("xdg-user-dir ");
      buf.append(dirs[i]);
      buf.append(';');
    }
    commands[2] = buf.toString();
    return runCommands(dirsLength, Charset.defaultCharset(), commands);
  }

  static String[] getWinDirs(String... guids) {

    int guidsLength = guids.length;
    StringBuilder buf = new StringBuilder(guidsLength * 68);
    for (int i = 0; i < guidsLength; i++) {
      buf.append("[Dir]::GetKnownFolderPath(\"");
      buf.append(guids[i]);
      buf.append("\")\n");
    }

    String encodedCommand = SCRIPT_START_BASE64 + toUTF16LEBase64(buf.toString() + "}");

    return runCommands(guidsLength, Charset.forName("UTF-8"),
        "powershell.exe",
        "-NoProfile",
        "-EncodedCommand",
        encodedCommand
    );
  }

  private static String toUTF16LEBase64(String script) {
    byte[] scriptInUtf16LEBytes = script.getBytes(Charset.forName("UTF-16LE"));
    if (base64EncodeMethod == null) {
      initBase64Encoding();
    }
    try {
      return (String) base64EncodeMethod.invoke(base64Encoder, scriptInUtf16LEBytes);
    } catch (Exception e) {
      throw new RuntimeException("Base64 encoding failed!", e);
    }
  }

  private static void initBase64Encoding() {
    try {
      base64Encoder = Class.forName("java.util.Base64").getMethod("getEncoder").invoke(null);
      base64EncodeMethod = base64Encoder.getClass().getMethod("encodeToString", byte[].class);
    } catch (Exception e1) {
      try {
        base64EncodeMethod = Class.forName("sun.misc.BASE64Encoder").getMethod("encode", byte[].class);
      } catch (Exception e2) {
        throw new RuntimeException(
            "Could not find any viable Base64 encoder! (java.util.Base64 failed with: " + e1.getMessage() + ")", e2);
      }
    }
  }

  private static String[] runCommands(int expectedResultLines, Charset charset, String... commands) {
    final ProcessBuilder processBuilder = new ProcessBuilder(commands);
    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String[] results = new String[expectedResultLines];
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
    try {
      for (int i = 0; i < expectedResultLines; i++) {
        String line = reader.readLine();
        if (i == 0 && operatingSystem == 'w' && line != null && line.startsWith(UTF8_BOM))
          line = line.substring(UTF8_BOM.length());
        results[i] = line;
      }
      return results;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      process.destroy();
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  static String macOSApplicationPath(String qualifier, String organization, String application) {
    StringBuilder buf = new StringBuilder(Math.max(stringLength(qualifier) + stringLength(organization) + stringLength(application), 0));
    boolean qualPresent = !isNullOrEmpty(qualifier);
    boolean orgPresent  = !isNullOrEmpty(organization);
    boolean appPresent  = !isNullOrEmpty(application);
    if (qualPresent) {
      buf.append(trimLowercaseReplaceWhitespace(qualifier, "-", false));
      if (orgPresent || appPresent)
        buf.append('.');
    }
    if (orgPresent) {
      buf.append(trimLowercaseReplaceWhitespace(organization, "-", false));
      if (appPresent)
        buf.append('.');
    }
    if (appPresent)
      buf.append(trimLowercaseReplaceWhitespace(application, "-", false));
    return buf.toString();
  }

  static String windowsApplicationPath(String qualifier, String organization, String application) {
    StringBuilder buf = new StringBuilder(Math.max(stringLength(organization) + stringLength(application), 0));
    boolean orgPresent = !isNullOrEmpty(organization);
    boolean appPresent = !isNullOrEmpty(application);
    if (orgPresent) {
      buf.append(organization);
      if (appPresent)
        buf.append('\\');
    }
    if (appPresent)
      buf.append(application);
    return buf.toString();
  }

  static String stripQualification(String value) {
    int startingPosition = value.lastIndexOf('.') + 1;
    return value.substring(startingPosition);
  }

  static int stringLength(String value) {
    if (value == null)
      return -1;
    else
      return value.length();
  }

  static String trimLowercaseReplaceWhitespace(String value, String replacement, boolean lowerCase) {
    StringBuilder buf = new StringBuilder(value.length());
    boolean charsBefore = false;
    int codePointCount = value.codePointCount(0, value.length());
    boolean replace = !replacement.isEmpty();
    for (int index = 0; index < codePointCount; index += 1) {
      int codepoint = value.codePointAt(index);
      if (codepoint == ' ') {
        if (charsBefore && replace && codePointExistsAndNotSpace(value, codePointCount, index + 1)) {
          buf.append('-');
          charsBefore = false;
        }
      } else {
        buf.appendCodePoint(lowerCase ? Character.toLowerCase(codepoint) : codepoint);
        charsBefore = true;
      }
    }
    return buf.toString();
  }

  private static boolean codePointExistsAndNotSpace(String value, int count, int index) {
    return index < count && value.codePointAt(index) != ' ';
  }
}
