package io.github.soc.directories;

import java.util.Locale;
import java.util.Objects;

import static io.github.soc.directories.Util.*;

public final class ProjectDirectories {

  private ProjectDirectories(
    final String projectName,
    final String projectCacheDir,
    final String projectConfigDir,
    final String projectDataDir,
    final String projectDataRoamingDir) {
    Objects.requireNonNull(projectName);
      this.projectName           = projectName;
      this.projectCacheDir       = projectCacheDir;
      this.projectConfigDir      = projectConfigDir;
      this.projectDataDir        = projectDataDir;
      this.projectDataRoamingDir = projectDataRoamingDir;
    }

  public final String projectName;
  public final String projectCacheDir;
  public final String projectConfigDir;
  public final String projectDataDir;
  public final String projectDataRoamingDir;

  public static ProjectDirectories fromUnprocessedString(String value) {
    String homeDir;
    String projectCacheDir;
    String projectConfigDir;
    String projectDataDir;
    String projectDataRoamingDir;
    switch (operatingSystem) {
      case LIN:
        homeDir = System.getenv("HOME");
        projectCacheDir       = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir + "/.cache/")       + value + "/";
        projectConfigDir      = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir + "/.config/")      + value + "/";
        projectDataDir        = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/share/") + value + "/";
        projectDataRoamingDir = projectDataDir;
        break;
      case MAC:
        homeDir = System.getenv("HOME");
        projectCacheDir       = homeDir + "/Library/Caches/"              + value + "/";
        projectConfigDir      = homeDir + "/Library/Preferences/"         + value + "/";
        projectDataDir        = homeDir + "/Library/Application Support/" + value + "/";
        projectDataRoamingDir = projectDataDir;
        break;
      case WIN:
        homeDir = null; // FIXME
        projectDataDir        = runPowerShellCommand("LocalApplicationData") + "/" + value + "/";
        projectDataRoamingDir = runPowerShellCommand("ApplicationData") + "/" + value + "/";
        projectConfigDir      = projectDataDir;
        projectCacheDir       = projectDataDir + "cache/";
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
    return new ProjectDirectories(value, projectCacheDir, projectConfigDir, projectDataDir, projectDataRoamingDir);
  }

  public static ProjectDirectories fromFullyQualifiedProjectName(String fullyQualifiedProjectName) {
    String name;
    switch (operatingSystem) {
      case LIN:
        name = stripQualification(fullyQualifiedProjectName).toLowerCase(Locale.ENGLISH).trim();
        break;
      case MAC:
        name = fullyQualifiedProjectName;
        break;
      case WIN:
        name = stripQualification(fullyQualifiedProjectName);
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
      }
    return fromUnprocessedString(name);
  }

  public static ProjectDirectories fromProjectName(String projectName) {
    String name;
    switch (operatingSystem) {
      case LIN:
        name = trimAndReplaceSpacesWithHyphensThenLowerCase(projectName);
        break;
      case MAC:
      case WIN:
        name = projectName;
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
      }
    return fromUnprocessedString(name);
  }

  private static final String operatingSystemName = System.getProperty("os.name");
  private static final char operatingSystem;
  private static final char LIN = 'l';
  private static final char MAC = 'm';
  private static final char WIN = 'w';
  static {
    final String os = operatingSystemName.toLowerCase(Locale.ENGLISH);
    if (os.contains("linux"))
      operatingSystem = LIN;
    else if (os.contains("mac"))
      operatingSystem = MAC;
    else if (os.contains("win"))
      operatingSystem = WIN;
    else
      throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
  };

  @Override
  public String toString() {
    return "ProjectDirectories on operating system '" + operatingSystemName + "':" +
        "  projectName           ='" + projectName + '\'' +
        "  projectCacheDir       ='" + projectCacheDir + '\'' +
        "  projectConfigDir      ='" + projectConfigDir + '\'' +
        "  projectDataDir        ='" + projectDataDir + '\'' +
        "  projectDataRoamingDir ='" + projectDataRoamingDir + '\'';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProjectDirectories that = (ProjectDirectories) o;

    if (!projectName.equals(that.projectName)) return false;
    if (projectCacheDir  != null ? !projectCacheDir .equals(that.projectCacheDir)  : that.projectCacheDir != null)
      return false;
    if (projectConfigDir != null ? !projectConfigDir.equals(that.projectConfigDir) : that.projectConfigDir != null)
      return false;
    if (projectDataDir   != null ? !projectDataDir  .equals(that.projectDataDir)   : that.projectDataDir != null)
      return false;
    return projectDataRoamingDir != null ? projectDataRoamingDir.equals(that.projectDataRoamingDir) : that.projectDataRoamingDir == null;
  }

  @Override
  public int hashCode() {
    int result = projectName.hashCode();
    result = 31 * result + (projectCacheDir != null ? projectCacheDir.hashCode() : 0);
    result = 31 * result + (projectConfigDir != null ? projectConfigDir.hashCode() : 0);
    result = 31 * result + (projectDataDir != null ? projectDataDir.hashCode() : 0);
    result = 31 * result + (projectDataRoamingDir != null ? projectDataRoamingDir.hashCode() : 0);
    return result;
  }
}
