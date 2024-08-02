package dev.dirs;

import dev.dirs.impl.Linux;
import dev.dirs.impl.Util;
import dev.dirs.impl.Windows;

/** {@code BaseDirectories} provides paths of user-invisible standard directories, following the conventions of the operating system the library is running on.
  * <p>
  * To compute the location of cache, config or data directories for individual projects or applications, use {@link ProjectDirectories} instead.
  *
  * <h2>Examples</h2>
  * <p>
  * All examples on this page are computed with a user named <em>Alice</em>.
  * <p>
  * Example of {@link ProjectDirectories#configDir} value in different operating systems:
  * <ul>
  * <li><b>Linux/BSD:</b> {@code /home/alice/.config}</li>
  * <li><b>macOS:</b> {@code /Users/Alice/Library/Preferences}</li>
  * <li><b>Windows:</b> {@code C:\Users\Alice\AppData\Roaming}</li>
  * </ul>
  */
public final class BaseDirectories {

  /** Returns the path to the user's home directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $HOME}</td>
    * <td>/home/alice</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}</td>
    * <td>/Users/Alice</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_Profile}}</td>
    * <td>C:\Users\Alice\</td>
    * </tr>
    * </table>
    */
  public final String homeDir;

  /** Returns the path to the user's cache directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_CACHE_HOME} or {@code $HOME}/.cache</td>
    * <td>/home/alice/.cache</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Caches</td>
    * <td>/Users/Alice/Library/Caches</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_LocalAppData}}\cache</td>
    * <td>C:\Users\Alice\AppData\Local</td>
    * </tr>
    * </table>
    */
  public final String cacheDir;

  /** Returns the path to the user's configuration directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_CONFIG_HOME} or {@code $HOME}/.config</td>
    * <td>/home/alice/.config</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support</td>
    * <td>/Users/Alice/Library/Application Support</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_RoamingAppData}}</td>
    * <td>C:\Users\Alice\AppData\Roaming</td>
    * </tr>
    * </table>
    */
  public final String configDir;

  /** Returns the path to the user's data directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_DATA_HOME} or {@code $HOME}/.local/share</td>
    * <td>/home/alice/.local/share</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support</td>
    * <td>/Users/Alice/Library/Application Support</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_RoamingAppData}}</td>
    * <td>C:\Users\Alice\AppData\Roaming</td>
    * </tr>
    * </table>
    */
  public final String dataDir;

  /** Returns the path to the user's local data directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_DATA_HOME} or {@code $HOME}/.local/share</td>
    * <td>/home/alice/.local/share</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support</td>
    * <td>/Users/Alice/Library/Application Support</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_LocalAppData}}</td>
    * <td>C:\Users\Alice\AppData\Local</td>
    * </tr>
    * </table>
    */
  public final String dataLocalDir;

  /** Returns the path to the user's executable directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_BIN_HOME} or {@code $XDG_DATA_HOME}/../bin or {@code $HOME}/.local/bin</td>
    * <td>/home/alice/.local/bin</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>–</td>
    * <td>{@code null}</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>–</td>
    * <td>{@code null}</td>
    * </tr>
    * </table>
    */
  public final String executableDir;

  /** Returns the path to the user's preference directory.
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux/BSD</td>
   * <td>{@code $XDG_CONFIG_HOME} or {@code $HOME}/.config</td>
   * <td>/home/alice/.config</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Library/Preferences</td>
   * <td>/Users/Alice/Library/Preferences</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_RoamingAppData}}</td>
   * <td>C:\Users\Alice\AppData\Roaming</td>
   * </tr>
   * </table>
   */
  public final String preferenceDir;

  /** Returns the path to the user's runtime directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_RUNTIME_DIR}</td>
    * <td>/run/user/1001/</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>–</td>
    * <td>{@code null}</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>–</td>
    * <td>{@code null}</td>
    * </tr>
    * </table>
    */
  public final String runtimeDir;

  /** Creates a new {@code BaseDirectories} instance.
    * <p>
    * The instance is an immutable snapshot of the state of the system at the time this method is invoked.
    * Subsequent changes to the state of the system are not reflected in instances created prior to such a change.
    *
    * @return A new {@code BaseDirectories} instance.
    */
  public static BaseDirectories get() {
    return new BaseDirectories();
  }

  private BaseDirectories() {
    switch (Constants.operatingSystem) {
      case Constants.LIN:
      case Constants.BSD:
      case Constants.SOLARIS:
      case Constants.IBMI:
      case Constants.AIX:
        homeDir       = System.getProperty("user.home");
        cacheDir      = Util.defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir, "/.cache");
        configDir     = Util.defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir, "/.config");
        dataDir       = Util.defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir, "/.local/share");
        dataLocalDir  = dataDir;
        executableDir = Linux.executableDir(homeDir, dataDir);
        preferenceDir = configDir;
        runtimeDir    = Linux.runtimeDir(null);
        break;
      case Constants.MAC:
        homeDir       = System.getProperty("user.home");
        cacheDir      = homeDir + "/Library/Caches/";
        configDir     = homeDir + "/Library/Application Support/";
        dataDir       = configDir;
        dataLocalDir  = configDir;
        executableDir = null;
        preferenceDir = homeDir + "/Library/Preferences/";
        runtimeDir    = null;
        break;
      case Constants.WIN:
        String[] winDirs = Windows.getWinDirs("5E6C858F-0E22-4760-9AFE-EA3317B67173", "3EB685DB-65F9-4CF6-A03A-E3EF65729F3D", "F1B32785-6FBA-4FCF-9D55-7B8E7F157091");
        homeDir       = winDirs[0];
        dataDir       = winDirs[1];
        dataLocalDir  = winDirs[2];
        configDir     = dataDir;
        cacheDir      = dataLocalDir;
        executableDir = null;
        preferenceDir = configDir;
        runtimeDir    = null;
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + Constants.operatingSystemName);
    }
  }

  @Override
  public String toString() {
    return "BaseDirectories (" + Constants.operatingSystemName + "):\n" +
        "  homeDir       = '" + homeDir        + "'\n" +
        "  cacheDir      = '" + cacheDir       + "'\n" +
        "  configDir     = '" + configDir      + "'\n" +
        "  dataDir       = '" + dataDir        + "'\n" +
        "  dataLocalDir  = '" + dataLocalDir   + "'\n" +
        "  executableDir = '" + executableDir  + "'\n" +
        "  preferenceDir = '" + preferenceDir + "'\n" +
        "  runtimeDir    = '" + runtimeDir     + "'\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BaseDirectories that = (BaseDirectories) o;

    if (homeDir       != null ? !homeDir      .equals(that.homeDir)       : that.homeDir       != null)
      return false;
    if (cacheDir      != null ? !cacheDir     .equals(that.cacheDir)      : that.cacheDir      != null)
      return false;
    if (configDir     != null ? !configDir    .equals(that.configDir)     : that.configDir     != null)
      return false;
    if (dataDir       != null ? !dataDir      .equals(that.dataDir)       : that.dataDir       != null)
      return false;
    if (dataLocalDir  != null ? !dataLocalDir .equals(that.dataLocalDir)  : that.dataLocalDir  != null)
      return false;
    if (executableDir != null ? !executableDir.equals(that.executableDir) : that.executableDir != null)
      return false;
    if (preferenceDir != null ? !preferenceDir.equals(that.preferenceDir) : that.preferenceDir != null)
      return false;
    if (runtimeDir     != null ? !runtimeDir    .equals(that.runtimeDir)     : that.runtimeDir     != null)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = 0;
    result = 31 * result + (homeDir       != null ? homeDir      .hashCode() : 0);
    result = 31 * result + (cacheDir      != null ? cacheDir     .hashCode() : 0);
    result = 31 * result + (configDir     != null ? configDir    .hashCode() : 0);
    result = 31 * result + (dataDir       != null ? dataDir      .hashCode() : 0);
    result = 31 * result + (dataLocalDir  != null ? dataLocalDir .hashCode() : 0);
    result = 31 * result + (executableDir != null ? executableDir.hashCode() : 0);
    result = 31 * result + (preferenceDir != null ? preferenceDir.hashCode() : 0);
    result = 31 * result + (runtimeDir    != null ? runtimeDir   .hashCode() : 0);
    return result;
  }
}
