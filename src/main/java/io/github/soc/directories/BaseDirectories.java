package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/** <code>BaseDirectories</code> provides paths of user-invisible standard directories, following the conventions of the operating system the library is running on.
  * <p>
  * It is a snapshot of the state of the system at the time this class is initialized.
  * <p>
  * To compute the location of cache, config or data directories for individual projects or applications, use {@link ProjectDirectories} instead.
  *
  * <h2>Examples</h2>
  *
  * All examples on this page are computed with a user named <em>Alice</em>.
  * <p>
  * <code style="white-space: pre">
  * BaseDirs.configDir
  * // Linux:   /home/alice/.config
  * // Windows: C:\Users\Alice\AppData\Roaming
  * // macOS:   /Users/Alice/Library/Preferences
  * </code>
  */
public final class BaseDirectories {

  private BaseDirectories() {
    throw new Error();
  }

  /** Returns the path to the user's home directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                | Example         |
    * | ------- | -------------------- | --------------- |
    * | Linux   | `$HOME`              | /home/alice     |
    * | macOS   | `$HOME`              | /Users/Alice    |
    * | Windows | `{FOLDERID_Profile}` | C:\Users\Alice\ |
    * </code>
    */
  public static final String homeDir;

  // base directories

  /** Returns the path to the user's cache directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                               | Example                      |
    * | ------- | ----------------------------------- | ---------------------------- |
    * | Linux   | `$XDG_CACHE_HOME` or `$HOME`/.cache | /home/alice/.cache           |
    * | macOS   | `$HOME`/Library/Caches              | /Users/Alice/Library/Caches  |
    * | Windows | `{FOLDERID_LocalAppData}`\cache     | C:\Users\Alice\AppData\Local |
    * </code>
    */
  public static final String cacheDir;
  /** Returns the path to the user's config directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                                 | Example                          |
    * | ------- | ------------------------------------- | -------------------------------- |
    * | Linux   | `$XDG_CONFIG_HOME` or `$HOME`/.config | /home/alice/.config              |
    * | macOS   | `$HOME`/Library/Preferences           | /Users/Alice/Library/Preferences |
    * | Windows | `{FOLDERID_RoamingAppData}`           | C:\Users\Alice\AppData\Roaming   |
    * </code>
    */
  public static final String configDir;
  /** Returns the path to the user's data directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                                    | Example                                  |
    * | ------- | ---------------------------------------- | ---------------------------------------- |
    * | Linux   | `$XDG_DATA_HOME` or `$HOME`/.local/share | /home/alice/.local/share                 |
    * | macOS   | `$HOME`/Library/Application Support      | /Users/Alice/Library/Application Support |
    * | Windows | `{FOLDERID_RoamingAppData}`              | C:\Users\Alice\AppData\Roaming           |
    * </code>
    */
  public static final String dataDir;
  /** Returns the path to the user's local data directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                                    | Example                                  |
    * | ------- | ---------------------------------------- | ---------------------------------------- |
    * | Linux   | `$XDG_DATA_HOME` or `$HOME`/.local/share | /home/alice/.local/share                 |
    * | macOS   | `$HOME`/Library/Application Support      | /Users/Alice/Library/Application Support |
    * | Windows | `{FOLDERID_LocalAppData}`                | C:\Users\Alice\AppData\Local             |
    * </code>
    */
  public static final String dataLocalDir;
  /** Returns the path to the user's executable directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                                                            | Example                |
    * | ------- | ---------------------------------------------------------------- | ---------------------- |
    * | Linux   | `$XDG_BIN_HOME` or `$XDG_DATA_HOME`/../bin or `$HOME`/.local/bin | /home/alice/.local/bin |
    * | macOS   | –                                                                | null                   |
    * | Windows | –                                                                | null                   |
    * </code>
    */
  public static final String executableDir;
  /** Returns the path to the user's runtime directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value              | Example         |
    * | ------- | ------------------ | --------------- |
    * | Linux   | `$XDG_RUNTIME_DIR` | /run/user/1001/ |
    * | macOS   | –                  | null            |
    * | Windows | –                  | null            |
    * </code>
    */
  public static final String runtimeDir;

  static {
    switch (operatingSystem) {
      case LIN:
      case BSD:
        homeDir       = System.getProperty("user.home");
        cacheDir      = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir, "/.cache");
        configDir     = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir, "/.config");
        dataDir       = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir, "/.local/share");
        dataLocalDir  = dataDir;
        executableDir = linuxExecutableDir(homeDir, dataDir);
        runtimeDir    = linuxRuntimeDir(homeDir, null);
        break;
      case MAC:
        homeDir       = System.getProperty("user.home");
        cacheDir      = homeDir + "/Library/Caches/";
        configDir     = homeDir + "/Library/Preferences/";
        dataDir       = homeDir + "/Library/Application Support/";
        dataLocalDir  = dataDir;
        executableDir = null;
        runtimeDir    = null;
        break;
      case WIN:
        homeDir       = runPowerShellCommand("UserProfile");
        dataDir       = runPowerShellCommand("ApplicationData");
        dataLocalDir  = runPowerShellCommand("LocalApplicationData");
        configDir     = dataDir;
        cacheDir      = dataLocalDir;
        executableDir = null;
        runtimeDir    = null;
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
  }
}
