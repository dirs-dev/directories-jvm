package io.github.soc.directories;

import static io.github.soc.directories.Util.defaultIfNullOrEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class WindowsBaseDirectories extends BaseDirectories {
  public WindowsBaseDirectories() {
    this.homeDir        = null;

    this.dataDir        = runPowerShellCommand("LocalApplicationData");
    this.configDir      = this.dataDir;
    this.cacheDir       = this.dataDir + "/cache";
    this.runtimeDir     = null;

    this.desktopDir     = runPowerShellCommand("Desktop");
    this.documentsDir   = runPowerShellCommand("Documents");
    this.downloadDir    = runPowerShellCommand("Downloads");
    this.musicDir       = runPowerShellCommand("Music");
    this.picturesDir    = runPowerShellCommand("Pictures");
    this.publicDir      = runPowerShellCommand("Public");
    this.templatesDir   = runPowerShellCommand("Templates");
    this.videosDir      = runPowerShellCommand("Videos");

    this.executablesDir = null;
    this.fontsDir       = null;
  }
}
