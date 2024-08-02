package dev.dirs;

import java.util.Locale;

public class Constants {

  static final String operatingSystemName = System.getProperty("os.name");
  public static final char operatingSystem;
  static final char LIN = 'l';
  static final char MAC = 'm';
  static final char WIN = 'w';
  static final char BSD = 'b';
  static final char SOLARIS = 's';
  static final char IBMI = 'i';
  static final char AIX = 'a';

  static {
    final String os = operatingSystemName.toLowerCase(Locale.ROOT);
    if (os.contains("linux"))
      operatingSystem = LIN;
    else if (os.contains("mac"))
      operatingSystem = MAC;
    else if (os.contains("windows"))
      operatingSystem = WIN;
    else if (os.contains("bsd"))
      operatingSystem = BSD;
    else if (os.contains("sunos"))
      operatingSystem = SOLARIS;
    else if (os.contains("os/400") || os.contains("os400"))
      operatingSystem = IBMI;
    else if (os.contains("aix"))
      operatingSystem = AIX;
    else
      throw new UnsupportedOperatingSystemException("directories are not supported on " + operatingSystemName);
  }

}
