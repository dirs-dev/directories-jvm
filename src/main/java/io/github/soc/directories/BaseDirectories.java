package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

public final class BaseDirectories {

  private BaseDirectories() {
    throw new Error();
  }

  static final String homeDir;

  // base directories
  static final String cacheDir;
  static final String configDir;
  static final String dataDir;
  static final String dataLocalDir;
  static final String executableDir;
  static final String runtimeDir;

  // user directories
  static final String audioDir;
  static final String desktopDir;
  static final String documentDir;
  static final String downloadDir;
  static final String fontDir;
  static final String pictureDir;
  static final String publicDir;
  static final String templateDir;
  static final String videoDir;

  static {
    switch (operatingSystem) {
      case LIN:
      case BSD:
        homeDir       = System.getenv("HOME");
        cacheDir      = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir, "/.cache/");
        configDir     = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir, "/.config/");
        dataDir       = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir, "/.local/share/");
        dataLocalDir  = dataDir;
        executableDir = linuxExecutableDir(homeDir, dataDir);
        runtimeDir    = linuxRuntimeDir(homeDir, null);
        desktopDir    = defaultIfNullOrEmpty(runXDGUserDir("DESKTOP"),      homeDir, "/Desktop/");
        documentDir   = defaultIfNullOrEmpty(runXDGUserDir("DOCUMENTS"),    homeDir, "/Documents/");
        downloadDir   = defaultIfNullOrEmpty(runXDGUserDir("DOWNLOAD"),     homeDir, "/Downloads/");
        audioDir      = defaultIfNullOrEmpty(runXDGUserDir("MUSIC"),        homeDir, "/Music/");
        pictureDir    = defaultIfNullOrEmpty(runXDGUserDir("PICTURES"),     homeDir, "/Pictures/");
        publicDir     = defaultIfNullOrEmpty(runXDGUserDir("PUBLICSHARE"),  homeDir, "/Public/");
        templateDir   = defaultIfNullOrEmpty(runXDGUserDir("TEMPLATES"),    homeDir, "/Templates/");
        videoDir      = defaultIfNullOrEmpty(runXDGUserDir("VIDEOS"),       homeDir, "/Videos/");
        fontDir       = defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"), "/fonts/",  homeDir, "/.local/share/fonts/");
        break;
      case MAC:
        homeDir       = System.getenv("HOME");
        cacheDir      = homeDir + "/Library/Caches/";
        configDir     = homeDir + "/Library/Preferences/";
        dataDir       = homeDir + "/Library/Application Support/";
        dataLocalDir  = dataDir;
        executableDir = null;
        runtimeDir    = null;
        audioDir      = homeDir + "/Music/";
        desktopDir    = homeDir + "/Desktop/";
        documentDir   = homeDir + "/Documents/";
        downloadDir   = homeDir + "/Downloads/";
        fontDir       = homeDir + "/Library/Fonts/";
        pictureDir    = homeDir + "/Pictures/";
        publicDir     = homeDir + "/Public/";
        templateDir   = null;
        videoDir      = homeDir + "/Movies/";
        break;
      case WIN:
        homeDir       = runPowerShellCommand("UserProfile");
        dataDir       = runPowerShellCommand("ApplicationData");
        dataLocalDir  = runPowerShellCommand("LocalApplicationData");
        configDir     = dataDir;
        cacheDir      = dataLocalDir + "/cache/";
        executableDir = null;
        runtimeDir    = null;
        audioDir      = runPowerShellCommand("Music");
        desktopDir    = runPowerShellCommand("Desktop");
        documentDir   = runPowerShellCommand("Documents");
        downloadDir   = runPowerShellCommand("Downloads");
        fontDir       = null;
        pictureDir    = runPowerShellCommand("Pictures");
        publicDir     = runPowerShellCommand("Public");
        templateDir   = runPowerShellCommand("Templates");
        videoDir      = runPowerShellCommand("Videos");
        break;
      default:
        throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    }
  }
}
