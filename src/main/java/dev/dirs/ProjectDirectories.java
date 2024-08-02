package dev.dirs;

import dev.dirs.impl.Linux;
import dev.dirs.impl.MacOs;
import dev.dirs.impl.Util;
import dev.dirs.impl.Windows;

import java.util.Objects;

/** {@code ProjectDirectories} computes the location of cache, config or data directories for a specific application,
  * which are derived from the standard directories and the name of the project/organization.
  *
  * <h2>Examples</h2>
  * All examples on this page are computed with a user named <em>Alice</em>,
  * and a {@code ProjectDirectories} instance created with the following information:
  * <pre>{@code ProjectDirectories.from("com", "Foo Corp", "Bar App")}</pre>
  * <p>
  * Example of {@link ProjectDirectories#configDir} value in different operating systems:
  * <ul>
  * <li><b>Linux/BSD:</b> {@code /home/alice/.config/barapp}</li>
  * <li><b>macOS:</b> {@code /Users/Alice/Library/Preferences/com.Foo-Corp.Bar-App}</li>
  * <li><b>Windows:</b> {@code C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App}</li>
  * </ul>
  */
public final class ProjectDirectories {

  private ProjectDirectories(
      final String projectPath,
      final String cacheDir,
      final String configDir,
      final String dataDir,
      final String dataLocalDir,
      final String preferenceDir,
      final String runtimeDir) {

    Objects.requireNonNull(projectPath);

    this.projectPath   = projectPath;
    this.cacheDir      = cacheDir;
    this.configDir     = configDir;
    this.dataDir       = dataDir;
    this.dataLocalDir  = dataLocalDir;
    this.preferenceDir = preferenceDir;
    this.runtimeDir    = runtimeDir;
  }

  /** Returns the project path fragment used to compute the project's cache/config/data directories.
    * <p>
    * The value is derived from the arguments provided to the {@link ProjectDirectories#from} call and is platform-dependent.
    */
  public final String projectPath;

  /** Returns the path to the project's cache directory,
    * in which {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_CACHE_HOME}/{@code <project_path>} or {@code $HOME}/.cache/{@code <project_path>}</td>
    * <td>/home/alice/.cache/barapp</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Caches/{@code <project_path>}</td>
    * <td>/Users/Alice/Library/Caches/com.Foo-Corp.Bar-App</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_LocalAppData}}\{@code <project_path>}\cache</td>
    * <td>C:\Users\Alice\AppData\Local\Foo Corp\Bar App\cache</td>
    * </tr>
    * </table>
    */
  public final String cacheDir;

  /** Returns the path to the project's configuration directory,
    * in which {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_CONFIG_HOME}/{@code <project_path>} or {@code $HOME}/.config/{@code <project_path>}</td>
    * <td>/home/alice/.config/barapp</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support/{@code <project_path>}</td>
    * <td>/Users/Alice/Library/Application Support/com.Foo-Corp.Bar-App</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_RoamingAppData}}\{@code <project_path>}\config</td>
    * <td>C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App\config</td>
    * </tr>
    * </table>
    */
  public final String configDir;

  /** Returns the path to the project's data directory,
    * in which {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_DATA_HOME}/{@code <project_path>} or {@code $HOME}/.local/share/{@code <project_path>}</td>
    * <td>/home/alice/.local/share/barapp</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support/{@code <project_path>}</td>
    * <td>/Users/Alice/Library/Application Support/com.Foo-Corp.Bar-App</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_RoamingAppData}}\{@code <project_path>}\data</td>
    * <td>C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App\data</td>
    * </tr>
    * </table>
    */
  public final String dataDir;

  /** Returns the path to the project's local data directory,
    * in which {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code $XDG_DATA_HOME}/{@code <project_path>} or {@code $HOME}/.local/share/{@code <project_path>}</td>
    * <td>/home/alice/.local/share/barapp</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>{@code $HOME}/Library/Application Support/{@code <project_path>}</td>
    * <td>/Users/Alice/Library/Application Support/com.Foo-Corp.Bar-App</td>
    * </tr>
    * <tr>
    * <td>Windows</td>
    * <td>{@code {FOLDERID_LocalAppData}}\{@code <project_path>}\data</td>
    * <td>C:\Users\Alice\AppData\Local\Foo Corp\Bar App\data</td>
    * </tr>
    * </table>
    */
  public final String dataLocalDir;

  /** Returns the path to the project's preference directory,
   * in which {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}.
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux/BSD</td>
   * <td>{@code $XDG_CONFIG_HOME}/{@code <project_path>} or {@code $HOME}/.config/{@code <project_path>}</td>
   * <td>/home/alice/.config/barapp</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Library/Preferences/{@code <project_path>}</td>
   * <td>/Users/Alice/Library/Preferences/com.Foo-Corp.Bar-App</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_RoamingAppData}}\{@code <project_path>}\config</td>
   * <td>C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App\config</td>
   * </tr>
   * </table>
   */
  public final String preferenceDir;

  /** Returns the path to the project's runtime directory.
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
    * <td>/run/user/1001/barapp</td>
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

  /** Creates a {@code ProjectDirectories} instance directly from a path.
    * <p>
    * The argument is used verbatim and is not adapted to operating system standards.
    * The use of {@link ProjectDirectories#fromPath} is strongly discouraged, as its results will
    * not follow operating system standards on at least two of three platforms.
    *
    * @param path A string used verbatim as the path fragment to derive the directory field values.
    *
    * @return A new {@code ProjectDirectories} instance, whose directory field values are directly derived from the {@code path} argument.
    */
  public static ProjectDirectories fromPath(String path) {
    String homeDir;
    String cacheDir;
    String configDir;
    String dataDir;
    String dataLocalDir;
    String preferenceDir;
    String runtimeDir = null;
    switch (Constants.operatingSystem) {
      case Constants.LIN:
      case Constants.BSD:
      case Constants.SOLARIS:
      case Constants.IBMI:
      case Constants.AIX:
        homeDir       = System.getProperty("user.home");
        cacheDir      = Util.defaultIfNullOrEmptyExtended(System.getenv("XDG_CACHE_HOME"),  path, homeDir + "/.cache/",       path);
        configDir     = Util.defaultIfNullOrEmptyExtended(System.getenv("XDG_CONFIG_HOME"), path, homeDir + "/.config/",      path);
        dataDir       = Util.defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"),   path, homeDir + "/.local/share/", path);
        dataLocalDir  = dataDir;
        preferenceDir = configDir;
        runtimeDir    = Linux.runtimeDir(path);
        break;
      case Constants.MAC:
        homeDir       = System.getProperty("user.home");
        cacheDir      = homeDir + "/Library/Caches/"              + path;
        configDir     = homeDir + "/Library/Application Support/" + path;
        dataDir       = homeDir + "/Library/Application Support/" + path;
        dataLocalDir  = dataDir;
        preferenceDir = homeDir + "/Library/Preferences/"         + path;
        break;
      case Constants.WIN:
        String[] winDirs = Windows.getWinDirs("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D", "F1B32785-6FBA-4FCF-9D55-7B8E7F157091");
        String appDataRoaming = winDirs[0] + '\\' + path;
        String appDataLocal   = winDirs[1] + '\\' + path;
        dataDir       = appDataRoaming + "\\data";
        dataLocalDir  = appDataLocal   + "\\data";
        configDir     = appDataRoaming + "\\config";
        cacheDir      = appDataLocal   + "\\cache";
        preferenceDir = configDir;
        break;
      default:
        throw new UnsupportedOperatingSystemException("Project directories are not supported on " + Constants.operatingSystemName);
    }
    return new ProjectDirectories(path, cacheDir, configDir, dataDir, dataLocalDir, preferenceDir, runtimeDir);
  }

  /** Creates a {@code ProjectDirectories} instance from values describing the project.
    * <p>
    * The use of {@link ProjectDirectories#from} – instead of {@link ProjectDirectories#fromPath} – is strongly encouraged,
    * as its results will follow operating system standards on Linux, macOS and Windows.
    *
    * @param qualifier    The reverse domain name notation of the application, excluding the
    *                     organization or application name itself.<br>
    *                     An empty string can be passed if no qualifier should be used (only affects macOS).<br>
    *                     Example values: {@code "com.example"}, {@code "org"}, {@code "uk.co"}, {@code "io"}, {@code ""}
    * @param organization The name of the organization that develops this application, or for which
    *                     the application is developed.<br>
    *                     An empty string can be passed if no organization should be used (only affects macOS
    *                     and Windows).<br>
    *                     Example values: {@code "Foo Corp"}, {@code "Alice and Bob Inc"}, {@code ""}
    * @param application  The name of the application itself.<br>
    *                     Example values: {@code "Bar App"}, {@code "ExampleProgram"}, {@code "Unicorn-Programme"}
    *
    * @return An instance of {@code ProjectDirectories}, whose directory field values are based on the
    * {@code qualifier}, {@code organization} and {@code application} arguments.
    */
  public static ProjectDirectories from(String qualifier, String organization, String application) {
    if (Util.isNullOrEmpty(organization) && Util.isNullOrEmpty(application))
      throw new UnsupportedOperationException("organization and application arguments cannot both be null/empty");
    String path;
    switch (Constants.operatingSystem) {
      case Constants.LIN:
      case Constants.BSD:
      case Constants.SOLARIS:
      case Constants.IBMI:
      case Constants.AIX:
        path = Util.trimLowercaseReplaceWhitespace(application, "", true);
        break;
      case Constants.MAC:
        path = MacOs.applicationPath(qualifier, organization, application);
        break;
      case Constants.WIN:
        path = Windows.applicationPath(qualifier, organization, application);
        break;
      default:
        throw new UnsupportedOperatingSystemException("Project directories are not supported on " + Constants.operatingSystemName);
    }
    return fromPath(path);
  }

  @Override
  public String toString() {
    return "ProjectDirectories (" + Constants.operatingSystemName + "):\n" +
        "  projectPath   = '" + projectPath + "'\n" +
        "  cacheDir      = '" + cacheDir + "'\n" +
        "  configDir     = '" + configDir + "'\n" +
        "  dataDir       = '" + dataDir + "'\n" +
        "  dataLocalDir  = '" + dataLocalDir + "'\n" +
        "  preferenceDir = '" + preferenceDir + "'\n" +
        "  runtimeDir    = '" + runtimeDir + "'\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProjectDirectories that = (ProjectDirectories) o;

    if (!projectPath.equals(that.projectPath)) return false;
    if (cacheDir      != null ? !cacheDir     .equals(that.cacheDir)      : that.cacheDir      != null)
      return false;
    if (configDir     != null ? !configDir    .equals(that.configDir)     : that.configDir     != null)
      return false;
    if (dataDir       != null ? !dataDir      .equals(that.dataDir)       : that.dataDir       != null)
      return false;
    if (dataLocalDir  != null ? !dataLocalDir .equals(that.dataLocalDir)  : that.dataLocalDir  != null)
      return false;
    if (preferenceDir != null ? !preferenceDir.equals(that.preferenceDir) : that.preferenceDir != null)
      return false;
    if (runtimeDir    != null ? !runtimeDir   .equals(that.runtimeDir)    : that.runtimeDir    != null)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = projectPath.hashCode();
    result = 31 * result + (cacheDir      != null ? cacheDir     .hashCode() : 0);
    result = 31 * result + (configDir     != null ? configDir    .hashCode() : 0);
    result = 31 * result + (dataDir       != null ? dataDir      .hashCode() : 0);
    result = 31 * result + (dataLocalDir  != null ? dataLocalDir .hashCode() : 0);
    result = 31 * result + (preferenceDir != null ? preferenceDir.hashCode() : 0);
    result = 31 * result + (runtimeDir    != null ? runtimeDir   .hashCode() : 0);
    return result;
  }
}
