package io.github.soc.directories;

public class MacProjectDirectories extends ProjectDirectories {

  public MacProjectDirectories(String projectName) {
    this.projectName = projectName;

    String homeDir = System.getenv("HOME");

    this.projectCacheDir  = homeDir + "/Library/Caches/"              + projectName + "/";
    this.projectConfigDir = homeDir + "/Library/Preferences/"         + projectName + "/";
    this.projectDataDir   = homeDir + "/Library/Application Support/" + projectName + "/";
  }
}
