package io.github.soc.directories;

public abstract class ProjectDirectories {

  protected String projectName;

  protected String projectCacheDir;
  protected String projectConfigDir;
  protected String projectDataDir;

  public static ProjectDirectories fromUnprocessedString(String value) {
    OperatingSystem os = OperatingSystem.get();
    return os.projectDirectoriesFromUnprocessedString(value);
  }

  public static ProjectDirectories fromFullyQualifiedProjectName(String fullyQualifiedProjectName) {
    OperatingSystem os = OperatingSystem.get();
    return os.projectDirectoriesFromFullyQualifiedProjectName(fullyQualifiedProjectName);
  }

  public static ProjectDirectories fromProjectName(String projectName) {
    OperatingSystem os = OperatingSystem.get();
    return os.projectDirectoriesFromFullyQualifiedProjectName(projectName);
  }
}
