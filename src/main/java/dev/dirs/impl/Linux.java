package dev.dirs.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Linux {

  private Linux() {
    throw new Error();
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

  public static String runtimeDir(String path) {
    String runDir = System.getenv("XDG_RUNTIME_DIR");
    if (Util.isNullOrEmpty(runDir))
      return null;
    else if (path == null)
      return runDir;
    else
      return runDir + '/' + path;
  }

  public static String executableDir(String homeDir, String dataDir) {
    String binDir = System.getenv("XDG_BIN_HOME");
    if (Util.isNullOrEmpty(binDir))
      return Util.defaultIfNullOrEmptyExtended(dataDir, "/../bin/", homeDir, "/.local/bin/");
    else
      return binDir;
  }

  public static String[] getXDGUserDirs(String... dirs) {
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
      return runCommands(dirsLength, commands);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String[] runCommands(int expectedResultLines, String... commands) throws IOException {
    final Process process = new ProcessBuilder(commands).start();

    String[] results = new String[expectedResultLines];
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    try {
      for (int i = 0; i < expectedResultLines; i++) {
        results[i] = reader.readLine();
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

}
