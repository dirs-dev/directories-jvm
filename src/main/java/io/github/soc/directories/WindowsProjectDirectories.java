package io.github.soc.directories;

public class WindowsProjectDirectories extends ProjectDirectories {

  public WindowsProjectDirectories(String projectName) {
    this.projectName = projectName;

    this.projectDataDir   = runPowerShellCommand("LocalApplicationData") + "/" + projectName + "/";
    this.projectConfigDir = this.projectDataDir;
    this.projectCacheDir  = this.projectDataDir + "cache/";
  }
}
