package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/**
 * {@code BaseDirectories} provides paths of user-invisible standard directories, following the conventions of the operating system the library is running on.
 * <p>
 * It is a snapshot of the state of the system at the time this class is initialized.
 * <p>
 * To compute the location of cache, config or data directories for individual projects or applications, use {@link ProjectDirectories} instead.
 *
 * <h2>Examples</h2>
 * <p>
 * All examples on this page are computed with a user named <em>Alice</em>.
 * <p>
 * Example of {@link ProjectDirectories#configDir} value in different operating systems:
 * <ul>
 * <li><b>Linux / BSD:</b> {@code /home/alice/.config}</li>
 * <li><b>macOS:</b> {@code /Users/Alice/Library/Preferences}</li>
 * <li><b>Windows:</b> {@code C:\Users\Alice\AppData\Roaming}</li>
 * </ul>
 */
public final class BaseDirectories {

  private BaseDirectories() {
    throw new Error();
  }

  /**
   * Returns the path to the user's home directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
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
  public static final String homeDir;

  /**
   * Returns the path to the user's cache directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
   * <td>{@code $XDG_CACHE_HOME} or {@code $HOME/}.cache</td>
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
  public static final String cacheDir;

  /**
   * Returns the path to the user's config directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
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
  public static final String configDir;

  /**
   * Returns the path to the user's data directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
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
  public static final String dataDir;

  /**
   * Returns the path to the user's local data directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
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
  public static final String dataLocalDir;

  /**
   * Returns the path to the user's executable directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
   * <td>{@code $XDG_BIN_HOME} or {@code $XDG_DATA_HOME}/../bin or {@code $HOME}/.local/bin</td>
   * <td>/home/alice/.local/bin</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * </table>
   */
  public static final String executableDir;

  /**
   * Returns the path to the user's runtime directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux / BSD</td>
   * <td>{@code $XDG_RUNTIME_DIR}</td>
   * <td>/run/user/1001/</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
<<<<<<< HEAD
   * <td>{@code null}</td>
=======
   * <td>-</td>
>>>>>>> 24fb77665455a0855aa9c67ccd36b2cc7bdbc33d
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
<<<<<<< HEAD
   * <td>{@code null}</td>
=======
   * <td>-</td>
>>>>>>> 24fb77665455a0855aa9c67ccd36b2cc7bdbc33d
   * <td>{@code null}</td>
   * </tr>
   * </table>
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
