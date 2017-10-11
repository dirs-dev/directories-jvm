package io.github.soc.directories;

import java.util.Locale;

public enum OperatingSystem {
  LINUX {
    @Override
    public ProjectDirectories projectDirectoriesFromUnprocessedString(String value) {
      return new LinuxProjectDirectories(value);
    }

    @Override
    public ProjectDirectories projectDirectoriesFromFullyQualifiedProjectName(String fullyQualifiedProjectName) {
      int startingPosition = fullyQualifiedProjectName.lastIndexOf('.') + 1;
      String name = fullyQualifiedProjectName.substring(startingPosition);
      return new LinuxProjectDirectories(name.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public ProjectDirectories projectDirectoriesFromProjectName(String projectName) {
      return new LinuxProjectDirectories(projectName.toLowerCase(Locale.ENGLISH));
    }
  },
  MAC {
    @Override
    public ProjectDirectories projectDirectoriesFromUnprocessedString(String value) {
      return new MacProjectDirectories(value);
    }

    @Override
    public ProjectDirectories projectDirectoriesFromFullyQualifiedProjectName(String projectName) {
      return new MacProjectDirectories(projectName);
    }

    @Override
    public ProjectDirectories projectDirectoriesFromProjectName(String projectName) {
      return new MacProjectDirectories(projectName);
    }
  },
  WINDOWS {
    @Override
    public ProjectDirectories projectDirectoriesFromUnprocessedString(String value) {
      return new WindowsProjectDirectories(value);
    }

    @Override
    public ProjectDirectories projectDirectoriesFromFullyQualifiedProjectName(String fullyQualifiedProjectName) {
      int startingPosition = fullyQualifiedProjectName.lastIndexOf('.') + 1;
      String name = fullyQualifiedProjectName.substring(startingPosition);
      return new WindowsProjectDirectories(name);
    }

    @Override
    public ProjectDirectories projectDirectoriesFromProjectName(String projectName) {
      return new WindowsProjectDirectories(projectName);
    }
  };

  public abstract ProjectDirectories projectDirectoriesFromUnprocessedString(String value);

  public abstract ProjectDirectories projectDirectoriesFromFullyQualifiedProjectName(String fullyQualifiedProjectName);

  public abstract ProjectDirectories projectDirectoriesFromProjectName(String projectName);

  private static final String    operatingSystemName = System.getProperty("os.name");
  private static OperatingSystem operatingSystem     = null;

  public static OperatingSystem get() {
    if (operatingSystem != null)
      return operatingSystem;

    final String os = operatingSystemName.toLowerCase(Locale.ENGLISH);
    if (os.contains("linux"))
      operatingSystem = LINUX;
    else if (os.contains("mac"))
      operatingSystem = MAC;
    else if (os.contains("windows"))
      operatingSystem = WINDOWS;
    else
      throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
    return operatingSystem;
  }
}
