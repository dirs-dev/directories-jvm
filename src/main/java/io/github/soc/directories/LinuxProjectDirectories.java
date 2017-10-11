package io.github.soc.directories;

import static io.github.soc.directories.Util.defaultIfNullOrEmpty;

public class LinuxProjectDirectories extends ProjectDirectories {

  public LinuxProjectDirectories(String projectName) {
    this.projectName = projectName;

    String homeDir = System.getenv("HOME");

    this.projectCacheDir  = defaultIfNullOrEmpty(System.getenv("XDG_CACHE_HOME"),  homeDir + "/.cache/"       + projectName + "/");
    this.projectConfigDir = defaultIfNullOrEmpty(System.getenv("XDG_CONFIG_HOME"), homeDir + "/.config/"      + projectName + "/");
    this.projectDataDir   = defaultIfNullOrEmpty(System.getenv("XDG_DATA_HOME"),   homeDir + "/.local/share/" + projectName + "/");
  }
}
