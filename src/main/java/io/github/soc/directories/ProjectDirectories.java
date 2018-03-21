package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/**
 * {@code ProjectDirectories} computes the location of cache, config or data directories for a specific application,
 * which are derived from the standard directories and the name of the project/organization.
 *
 * <h2>Examples</h2>
 * All examples on this page are computed with a user named <em>Alice</em>,
 * and a {@code ProjectDirectories} instance created with the following information:
 * <pre>{@code ProjectDirectories.from("com", "Foo Corp", "Bar App")}</pre>
 * <p>
 * Example of {@link ProjectDirectories#configDir} value in different operating systems:
 * <ul>
 * <li><b>Linux / BSD:</b> {@code /home/alice/.config/barapp}</li>
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
      final String runtimeDir) {

    requireNonNull(projectPath);

    this.projectPath  = projectPath;
    this.cacheDir     = cacheDir;
    this.configDir    = configDir;
    this.dataDir      = dataDir;
    this.dataLocalDir = dataLocalDir;
    this.runtimeDir   = runtimeDir;
  }

  /**
   * Returns the project path fragment used to compute the project's cache/config/data directories.
   * <p>
   * The value is derived from the arguments provided to the {@link ProjectDirectories#from} call and is platform-dependent.
   */
  public final String projectPath;

  /**
   * Returns the path to the project's cache directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
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
   * Where {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}
   */
  public final String cacheDir;

  /**
   * Returns the path to the project's config directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
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
   * Where {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}
   */
  public final String configDir;

  /**
   * Returns the path to the project's data directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
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
   * Where {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}
   */
  public final String dataDir;

  /**
   * Returns the path to the project's local data directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
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
   * Where {@code <project_path>} is the value of {@link ProjectDirectories#projectPath}
   */
  public final String dataLocalDir;

  /**
   * Returns the path to the project's runtime directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code $XDG_RUNTIME_DIR}</td>
   * <td>/run/user/1001/barapp</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code null}</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code null}</td>
   * <td>{@code null}</td>
   * </tr>
   * </table>
   */
  public final String runtimeDir;

  /**
   * Creates a {@code ProjectDirectories} instance directly from a path.
   * <p>
   * The argument is used verbatim and is not adapted to operating system standards.
   * The use of {@link ProjectDirectories#fromPath} is strongly discouraged, as its results will
   * not follow operating system standards on at least two of three platforms.
   *
   * @param path A string used verbatim as the path fragment to derive the directory field values.
   *
   * @return An instance of {@code ProjectDirectories}, whose directory field values are directly derived from the {@code path} argument.
   */
  public static ProjectDirectories fromPath(String path) {
    String homeDir;
    String cacheDir;
    String configDir;
    String dataDir;
    String dataLocalDir;
    String runtimeDir = null;
    switch (operatingSystem) {
      case LIN:
      case BSD:
        homeDir      = System.getProperty("user.home");
        cacheDir     = defaultIfNullOrEmptyExtended(System.getenv("XDG_CACHE_HOME"),  path, homeDir + "/.cache/",       path);
        configDir    = defaultIfNullOrEmptyExtended(System.getenv("XDG_CONFIG_HOME"), path, homeDir + "/.config/",      path);
        dataDir      = defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"),   path, homeDir + "/.local/share/", path);
        dataLocalDir = dataDir;
        runtimeDir   = linuxRuntimeDir(homeDir, path);
        break;
      case MAC:
        homeDir      = System.getProperty("user.home");
        cacheDir     = homeDir + "/Library/Caches/"              + path;
        configDir    = homeDir + "/Library/Preferences/"         + path;
        dataDir      = homeDir + "/Library/Application Support/" + path;
        dataLocalDir = dataDir;
        break;
      case WIN:
        final String appDataRoaming = runPowerShellCommand("ApplicationData") + '/' + path;
        final String appDataLocal   = runPowerShellCommand("LocalApplicationData") + '/' + path;
        dataDir      = appDataRoaming + "/data";
        dataLocalDir = appDataLocal   + "/data";
        configDir    = appDataRoaming + "/config";
        cacheDir     = appDataLocal   + "/cache";
        break;
      default:
        throw new UnsupportedOperatingSystemException("Project directories are not supported on " + operatingSystemName);
    }
    return new ProjectDirectories(path, cacheDir, configDir, dataDir, dataLocalDir, runtimeDir);
  }

  /**
   * Creates a {@code ProjectDirectories} instance from values describing the project.
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
    String path;
    StringBuilder buf;
    boolean orgPresent;
    boolean appPresent;
    switch (operatingSystem) {
      case LIN:
      case BSD:
        path = trimLowercaseReplaceWhitespace(application, "");
        break;
      case MAC:
        buf = new StringBuilder(Math.max(stringLength(qualifier) + stringLength(organization) + stringLength(application), 0));
        boolean qualPresent = !isNullOrEmpty(qualifier);
        orgPresent = !isNullOrEmpty(organization);
        appPresent = !isNullOrEmpty(application);
        if (qualPresent)
          buf.append(qualifier.replace(' ', '-'));
        if (orgPresent || appPresent)
          buf.append('.');
        if (orgPresent)
          buf.append(organization.replace(' ', '-'));
        if (appPresent)
          buf.append('.');
        if (!isNullOrEmpty(application))
          buf.append(application.replace(' ', '-'));
        path = buf.toString();
        break;
      case WIN:
        buf = new StringBuilder(Math.max(stringLength(organization) + stringLength(application), 0));
        orgPresent = !isNullOrEmpty(organization);
        appPresent = !isNullOrEmpty(application);
        if (orgPresent)
          buf.append(organization);
        if (appPresent)
          buf.append('/');
        if (appPresent)
          buf.append(application);
        path = buf.toString();
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
    return fromPath(path);
  }

  @Override
  public String toString() {
    return "ProjectDirectories on operating system '" + operatingSystemName + "':\n" +
        "  projectPath  = '" + projectPath + "'\n" +
        "  cacheDir     = '" + cacheDir + "'\n" +
        "  configDir    = '" + configDir + "'\n" +
        "  dataDir      = '" + dataDir + "'\n" +
        "  dataLocalDir = '" + dataLocalDir + "'\n" +
        "  runtimeDir   = '" + runtimeDir + "'\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProjectDirectories that = (ProjectDirectories) o;

    if (!projectPath.equals(that.projectPath)) return false;
    if (cacheDir     != null ? !cacheDir    .equals(that.cacheDir)     : that.cacheDir != null)
      return false;
    if (configDir    != null ? !configDir   .equals(that.configDir)    : that.configDir != null)
      return false;
    if (dataDir      != null ? !dataDir     .equals(that.dataDir)      : that.dataDir != null)
      return false;
    if (dataLocalDir != null ? !dataLocalDir.equals(that.dataLocalDir) : that.dataLocalDir != null)
      return false;
    if (runtimeDir   != null ? !runtimeDir  .equals(that.runtimeDir)   : that.runtimeDir != null)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = projectPath.hashCode();
    result = 31 * result + (cacheDir     != null ? cacheDir    .hashCode() : 0);
    result = 31 * result + (configDir    != null ? configDir   .hashCode() : 0);
    result = 31 * result + (dataDir      != null ? dataDir     .hashCode() : 0);
    result = 31 * result + (dataLocalDir != null ? dataLocalDir.hashCode() : 0);
    result = 31 * result + (runtimeDir   != null ? runtimeDir  .hashCode() : 0);
    return result;
  }
}
