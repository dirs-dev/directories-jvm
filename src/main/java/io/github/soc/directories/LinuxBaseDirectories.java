package io.github.soc.directories;

import static io.github.soc.directories.Util.defaultIfNullOrEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class LinuxBaseDirectories extends BaseDirectories {

  public LinuxBaseDirectories() {
    this.homeDir        = System.getenv("HOME");

    this.cacheDir       = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir + "/.cache/");
    this.configDir      = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir + "/.config/");
    this.dataDir        = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/share/");
    this.runtimeDir     = System.getenv("XDG_RUNTIME_DIR");

    this.desktopDir     = defaultIfNullOrEmpty(runXDGUserDir("DESKTOP"),         homeDir + "/Desktop/");
    this.documentsDir   = defaultIfNullOrEmpty(runXDGUserDir("DOCUMENTS"),       homeDir + "/Documents/");
    this.downloadDir    = defaultIfNullOrEmpty(runXDGUserDir("DOWNLOAD"),        homeDir + "/Downloads/");
    this.musicDir       = defaultIfNullOrEmpty(runXDGUserDir("MUSIC"),           homeDir + "/Music/");
    this.picturesDir    = defaultIfNullOrEmpty(runXDGUserDir("PICTURES"),        homeDir + "/Pictures/");
    this.publicDir      = defaultIfNullOrEmpty(runXDGUserDir("PUBLICSHARE"),     homeDir + "/Public/");
    this.templatesDir   = defaultIfNullOrEmpty(runXDGUserDir("TEMPLATES"),       homeDir + "/Templates/");
    this.videosDir      = defaultIfNullOrEmpty(runXDGUserDir("VIDEOS"),          homeDir + "/Videos/");
    
    this.executablesDir = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/bin/");
    this.fontsDir       = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/share/fonts/");
  }

  private static String runXDGUserDir(String argument) {
    ProcessBuilder processBuilder = new ProcessBuilder("xdg-user-dir", argument);
    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      return reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      process.destroy();
    }
  }
}
