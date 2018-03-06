package io.github.soc.directories;

import java.util.Locale;

import static io.github.soc.directories.Util.*;

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

  public final String projectPath;
  public final String cacheDir;
  public final String configDir;
  public final String dataDir;
  public final String dataLocalDir;
  public final String runtimeDir;

  public static ProjectDirectories fromPath(String path) {
    String path_ = path + '/';
    String homeDir;
    String cacheDir;
    String configDir;
    String dataDir;
    String dataLocalDir;
    String runtimeDir = null;
    switch (operatingSystem) {
      case LIN:
      case BSD:
        homeDir      = System.getenv("HOME");
        cacheDir     = defaultIfNullOrEmptyExtended(System.getenv("XDG_CACHE_HOME"),  '/' + path_, homeDir + "/.cache/",       path_);
        configDir    = defaultIfNullOrEmptyExtended(System.getenv("XDG_CONFIG_HOME"), '/' + path_, homeDir + "/.config/",      path_);
        dataDir      = defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"),   '/' + path_, homeDir + "/.local/share/", path_);
        dataLocalDir = dataDir;
        runtimeDir   = linuxRuntimeDir(homeDir, path_);
        break;
      case MAC:
        homeDir      = System.getenv("HOME");
        cacheDir     = homeDir + "/Library/Caches/"              + path_;
        configDir    = homeDir + "/Library/Preferences/"         + path_;
        dataDir      = homeDir + "/Library/Application Support/" + path_;
        dataLocalDir = dataDir;
        break;
      case WIN:
        dataDir      = runPowerShellCommand("ApplicationData")      + '/' + path_;
        dataLocalDir = runPowerShellCommand("LocalApplicationData") + '/' + path_;
        configDir    = dataDir;
        cacheDir     = dataDir + "cache/";
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
    return new ProjectDirectories(path_, cacheDir, configDir, dataDir, dataLocalDir, runtimeDir);
  }

  public static ProjectDirectories from(String qualifier, String organization, String project) {
    String path;
    StringBuilder buf;
    boolean orgPresent;
    boolean projPresent;
    switch (operatingSystem) {
      case LIN:
      case BSD:
        path = trimLowercaseReplaceWhitespace(project, "");
        break;
      case MAC:
        buf = new StringBuilder(Math.max(stringLength(qualifier) + stringLength(organization) + stringLength(project), 0));
        boolean qualPresent = !isNullOrEmpty(qualifier);
                orgPresent  = !isNullOrEmpty(organization);
                projPresent = !isNullOrEmpty(project);
        if (qualPresent)
          buf.append(qualifier.replace(' ', '-'));
          if (orgPresent || projPresent)
            buf.append('.');
        if (orgPresent)
          buf.append(organization.replace(' ', '-'));
          if (projPresent)
            buf.append('.');
        if (!isNullOrEmpty(project))
          buf.append(project.replace(' ', '-'));
        path = buf.toString();
      case WIN:
        buf = new StringBuilder(Math.max(stringLength(organization) + stringLength(project), 0));
        orgPresent  = !isNullOrEmpty(organization);
        projPresent = !isNullOrEmpty(project);
        if (orgPresent)
          buf.append(organization);
          if (projPresent)
            buf.append('/');
        if (projPresent)
          buf.append(project);
        path = buf.toString();
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
      }
    return fromPath(path);
  }

  @Override
  public String toString() {
    return "ProjectDirectories on operating system '" + operatingSystemName + "':" +
        "  projectPath  = '" + projectPath + '\'' +
        "  cacheDir     = '" + cacheDir + '\'' +
        "  configDir    = '" + configDir + '\'' +
        "  dataDir      = '" + dataDir + '\'' +
        "  dataLocalDir = '" + dataLocalDir + '\'' +
        "  runtimeDir   = '" + runtimeDir + '\'';
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
