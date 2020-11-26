package dev.dirs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    try {
      return runCommands(dirsLength, Charset.defaultCharset(), commands);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static Map<String, String> guidToEnv = new HashMap<>();
  static {
    //See https://docs.microsoft.com/en-us/windows/win32/shell/knownfolderid for documentation
    guidToEnv.put("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D", System.getenv("APPDATA"));
    guidToEnv.put("5E6C858F-0E22-4760-9AFE-EA3317B67173", System.getenv("USERPROFILE"));
    guidToEnv.put("F1B32785-6FBA-4FCF-9D55-7B8E7F157091", System.getenv("LOCALAPPDATA"));
    guidToEnv.put("DFDF76A2-C82A-4D63-906A-5644AC457385", System.getenv("PUBLIC"));

    //appdata path
    String appDataPath = guidToEnv.get("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D");
    if(appDataPath != null){
      guidToEnv.put("A63293E8-664E-48DB-A079-DF759E0509F7",appDataPath + "\\Microsoft\\Windows\\Templates");
    }

    //userprofile
    String userProfilePath = guidToEnv.get("5E6C858F-0E22-4760-9AFE-EA3317B67173");
    if(userProfilePath != null) {
      guidToEnv.put("B4BFCC3A-DB2C-424C-B029-7FE99A87C641", userProfilePath + "\\Desktop");
      guidToEnv.put("FDD39AD0-238F-46AF-ADB4-6C85480369C7", userProfilePath + "\\Documents");
      guidToEnv.put("374DE290-123F-4565-9164-39C4925E467B", userProfilePath + "\\Downloads");
      guidToEnv.put("4BD8D571-6D19-48D3-BE97-422220080E43", userProfilePath + "\\Music");
      guidToEnv.put("33E28130-4E1E-4676-835A-98395C3BC3BB", userProfilePath + "\\Pictures");
      guidToEnv.put("18989B1D-99B5-455B-841C-AB7C74E4DDFC", userProfilePath + "\\Videos");
    }
  }

  static String[] getWinDirs(String... guids) {
    String[] fromEnv = getWinDirsFromEnv(guids);
    return fromEnv != null
            ? fromEnv
            : getWinDirsFallback(guids);
  }

  static String[] getWinDirsFromEnv(String... guids) {
    String[] paths = new String[guids.length];
    for (int i = 0; i < guids.length; i++) {
      final String path = guidToEnv.get(guids[i]);
      if(path == null) return null;
      else {
        paths[i] = path;
      }
    }
    return paths;
  }

  static String[] getWinDirsFallback(String... guids) {
    int guidsLength = guids.length;
    StringBuilder buf = new StringBuilder(guidsLength * 68);
    for (int i = 0; i < guidsLength; i++) {
      buf.append("[Dir]::GetKnownFolderPath(\"");
      buf.append(guids[i]);
      buf.append("\")\n");
    }

    String encodedCommand = SCRIPT_START_BASE64 + toUTF16LEBase64(buf + "}");
    String path = System.getenv("Path");
    String[] dirs = path == null ? new String[0] : path.split(File.pathSeparator);
    if (dirs.length == 0) {
      return windowsFallback(guidsLength, encodedCommand);
    }
    try {
      return runWinCommands(guidsLength, dirs, encodedCommand);
    } catch (IOException e) {
      return windowsFallback(guidsLength, encodedCommand);
    }
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

  private static String[] runCommands(int expectedResultLines, Charset charset, String... commands) throws IOException {
    final Process process = new ProcessBuilder(commands).start();

    String[] results = new String[expectedResultLines];
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
    try {
      for (int i = 0; i < expectedResultLines; i++) {
        String line = reader.readLine();
        if (line == null) throw new IOException("no output from process");
        if (i == 0 && operatingSystem == 'w' && line != null && line.startsWith(UTF8_BOM))
          line = line.substring(UTF8_BOM.length());
        results[i] = line;
      }
      return results;
    } finally {
      process.destroy();
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static String[] runWinCommands(int guidsLength, String[] dirs, String encodedCommand) throws IOException {
    // legacy powershell.exe seems to run faster than pwsh.exe so prefer it if available
    String[] commands = { "powershell.exe", "pwsh.exe" };
    IOException firstException = null;
    for (String dir : dirs) {
      for (String command : commands) {
        File commandFile = new File(dir, command);
        if (commandFile.exists()) {
          try {
            return runCommands(guidsLength, Charset.forName("UTF-8"),
                commandFile.toString(),
                "-NoProfile",
                "-EncodedCommand",
                encodedCommand
            );
          } catch (IOException e) {
            firstException = firstException == null ? e : firstException;
          }
        }
      }
    }
    if (firstException != null) {
      throw firstException;
    }
    else throw new IOException("no directories");
  }

  private static String[] windowsFallback(int guidsLength, String encodedCommand) {
    File powerShellBase = new File("C:\\Program Files\\Powershell");
    String[] powerShellDirs = powerShellBase.list();
    if (powerShellDirs == null) {
      powerShellDirs = new String[0];
    }
    String[] allPowerShellDirs = new String[powerShellDirs.length + 1];

    // legacy powershell.exe seems to run faster than pwsh.exe so prefer it if available
    String systemRoot = System.getenv("SystemRoot");
    if (systemRoot == null) {
      systemRoot = "C:\\Windows";
    }
    allPowerShellDirs[0] = systemRoot + "\\System32\\WindowsPowerShell\\v1.0\\";

    for (int i = 0; i < powerShellDirs.length; ++i) {
      allPowerShellDirs[i + 1] = new File(powerShellBase, powerShellDirs[i]).toString();
    }
    try {
      return runWinCommands(guidsLength, allPowerShellDirs, encodedCommand);
    } catch (final IOException ex) {
      throw new RuntimeException("Couldn't find pwsh.exe or powershell.exe on path or in default system locations", ex);
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
