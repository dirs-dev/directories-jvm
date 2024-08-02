package dev.dirs.impl;

import dev.dirs.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

public class Windows {

  private Windows() {}

  private static final String UTF8_BOM = "\ufeff";
  private static Object base64Encoder = null;
  private static Method base64EncodeMethod = null;
  // This string needs to end up being a multiple of 3 bytes after conversion to UTF-16. (It is currently 1200 bytes.)
  // This is because Base64 converts 3 bytes to 4 letters; other numbers of bytes would introduce padding, which
  // would make it harder to simply concatenate this precomputed string with whatever directories the user requests.
  static final String SCRIPT_START_BASE64 = Constants.operatingSystem == 'w' ? toUTF16LEBase64("& {\n" +
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

  public static String[] getWinDirs(String... guids) {
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
        if (i == 0 && Constants.operatingSystem == 'w' && line != null && line.startsWith(UTF8_BOM))
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
            String[] stdout;
            // try to run using powershell V2 to bypass constrained language mode
            // note that this has been deprecated in new version of Windows
            // https://devblogs.microsoft.com/powershell/windows-powershell-2-0-deprecation/
            // for some set up, running this requires installation of extra dependency on Windows host
            stdout = runCommands(guidsLength, Charset.forName("UTF-8"),
                commandFile.toString(),
                "-version",
                "2",
                "-NoProfile",
                "-EncodedCommand",
                encodedCommand
            );
            if (stdout[0] != null) return stdout;

            // fall-forward to higher version of powershell
            stdout = runCommands(guidsLength, Charset.forName("UTF-8"),
                commandFile.toString(),
                "-NoProfile",
                "-EncodedCommand",
                encodedCommand
            );
            return stdout;
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

  public static String applicationPath(String qualifier, String organization, String application) {
    StringBuilder buf = new StringBuilder(Math.max(Util.stringLength(organization) + Util.stringLength(application), 0));
    boolean orgPresent = !Util.isNullOrEmpty(organization);
    boolean appPresent = !Util.isNullOrEmpty(application);
    if (orgPresent) {
      buf.append(organization);
      if (appPresent)
        buf.append('\\');
    }
    if (appPresent)
      buf.append(application);
    return buf.toString();
  }
}
