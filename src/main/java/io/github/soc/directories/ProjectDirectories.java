package io.github.soc.directories;

import java.util.Locale;

import static io.github.soc.directories.Util.*;

public final class ProjectDirectories {

  private ProjectDirectories(
    final String projectName,
    final String projectCacheDir,
    final String projectConfigDir,
    final String projectDataDir) {
      this.projectName      = projectName;
      this.projectCacheDir  = projectCacheDir;
      this.projectConfigDir = projectConfigDir;
      this.projectDataDir   = projectDataDir;
    }

  final public String projectName;
  final public String projectCacheDir;
  final public String projectConfigDir;
  final public String projectDataDir;

  public static ProjectDirectories fromUnprocessedString(String value) {
    String homeDir;
    String projectCacheDir;
    String projectConfigDir;
    String projectDataDir;
    switch (operatingSystem) {
      case LIN:
        homeDir = System.getenv("HOME");
        projectCacheDir  = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir + "/.cache/")       + value + "/";
        projectConfigDir = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir + "/.config/")      + value + "/";
        projectDataDir   = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/share/") + value + "/";
        break;
      case MAC:
        homeDir = System.getenv("HOME");
        projectCacheDir  = homeDir + "/Library/Caches/"              + value + "/";
        projectConfigDir = homeDir + "/Library/Preferences/"         + value + "/";
        projectDataDir   = homeDir + "/Library/Application Support/" + value + "/";
        break;
      case WIN:
        homeDir = null; // FIXME
        projectDataDir   = runPowerShellCommand("LocalApplicationData") + "/" + value + "/";
        projectConfigDir = projectDataDir;
        projectCacheDir  = projectDataDir + "cache/";
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
    return new ProjectDirectories(value, projectCacheDir, projectConfigDir, projectDataDir);
  }

  public static ProjectDirectories fromFullyQualifiedProjectName(String fullyQualifiedProjectName) {
    String name;
    int startingPosition = -1;
    switch (operatingSystem) {
      case LIN:
        startingPosition = fullyQualifiedProjectName.lastIndexOf('.') + 1;
        name = fullyQualifiedProjectName.substring(startingPosition).toLowerCase(Locale.ENGLISH);
        break;
      case MAC:
        name = fullyQualifiedProjectName;
        break;
      case WIN:
        startingPosition = fullyQualifiedProjectName.lastIndexOf('.') + 1;
        name = fullyQualifiedProjectName.substring(startingPosition);
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
        name = projectName.toLowerCase(Locale.ENGLISH);
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
}
