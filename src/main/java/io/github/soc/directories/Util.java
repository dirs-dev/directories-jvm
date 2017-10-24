package io.github.soc.directories;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Locale;

final class Util {
  private Util() {
    throw new Error();
  }

  static final String operatingSystemName = System.getProperty("os.name");
  static final char operatingSystem;
  static final char LIN = 'l';
  static final char MAC = 'm';
  static final char WIN = 'w';

  static {
    final String os = operatingSystemName.toLowerCase(Locale.ENGLISH);
    if (os.contains("linux"))
      operatingSystem = LIN;
    else if (os.contains("mac"))
      operatingSystem = MAC;
    else if (os.contains("windows"))
      operatingSystem = WIN;
    else
      throw new UnsupportedOperatingSystemException("Base directories are not supported on " + operatingSystemName);
  }
  
  static void requireNonNull(Object value) {
    if (value == null)
      throw new NullPointerException();
  }

  static String defaultIfNullOrEmpty(String value, String fallbackValue, String arg) {
    requireNonNull(arg);
    if (value == null || value.isEmpty())
      return fallbackValue + arg;
    else
      return value;
  }

  static String defaultIfNullOrEmptyExtended(String value, String valueArg, String fallbackValue, String fallbackArg) {
    requireNonNull(valueArg);
    requireNonNull(fallbackValue);
    requireNonNull(fallbackArg);
    if (value == null || value.isEmpty())
      return fallbackValue + fallbackArg;
    else
      return value;
  }

  static String runXDGUserDir(String argument) {
    ProcessBuilder processBuilder = new ProcessBuilder("xdg-user-dir", argument);
    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    try {
      return reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      process.destroy();
      try {
        reader.close();
      } catch (IOException e) {
      	return null;
      }
    }
  }

  static String runPowerShellCommand(String argument) {
    ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command",
        "[Environment]::GetFolderPath([Environment+SpecialFolder]::" + argument + ")");
    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    try {
      return reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      process.destroy();
      try {
        reader.close();
      } catch (IOException e) {
    	return null;
      }
    }
  }

  static String stripQualification(String value) {
    int startingPosition = value.lastIndexOf('.') + 1;
    return value.substring(startingPosition);
  }

  static String trimAndReplaceSpacesWithHyphensThenLowerCase(String value) {
    StringBuilder buf = new StringBuilder(value.length());
    boolean charsBefore = false;
    int codePointCount = value.codePointCount(0, value.length());
    for (int index = 0; index < codePointCount; index += 1) {
      int codepoint = value.codePointAt(index);
      if (codepoint == ' ') {
        if (charsBefore && codePointExistsAndNotSpace(value, codePointCount, index+1)) {
          buf.append('-');
          charsBefore = false;
        }
      } else {
        buf.appendCodePoint(Character.toLowerCase(codepoint));
        charsBefore = true;
      }
    }
    return buf.toString();
  }

  private static boolean codePointExistsAndNotSpace(String value, int count, int index) {
    return index < count && value.codePointAt(index) != ' ';
  }
}
