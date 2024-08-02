package dev.dirs.impl;

import dev.dirs.UnsupportedOperatingSystemException;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

public final class Util {

  private Util() {}

  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static String defaultIfNullOrEmpty(String value, String fallbackValue, String fallbackArg) {
    requireNonNull(fallbackArg);
    if (isNullOrEmpty(value))
      return Linux.ensureSingleSlash(fallbackValue, fallbackArg);
    else
      return value;
  }

  public static String defaultIfNullOrEmptyExtended(String value, String valueArg, String fallbackValue, String fallbackArg) {
    requireNonNull(valueArg);
    requireNonNull(fallbackValue);
    requireNonNull(fallbackArg);
    if (isNullOrEmpty(value))
      return Linux.ensureSingleSlash(fallbackValue, fallbackArg);
    else
      return Linux.ensureSingleSlash(value, valueArg);
  }

  public static String stripQualification(String value) {
    int startingPosition = value.lastIndexOf('.') + 1;
    return value.substring(startingPosition);
  }

  static int stringLength(String value) {
    if (value == null)
      return -1;
    else
      return value.length();
  }

  public static String trimLowercaseReplaceWhitespace(String value, String replacement, boolean lowerCase) {
    StringBuilder buf = new StringBuilder(value.length());
    boolean charsBefore = false;
    int codePointCount = value.codePointCount(0, value.length());
    boolean replace = !replacement.isEmpty();
    for (int index = 0; index < codePointCount; index += 1) {
      int codepoint = value.codePointAt(index);
      if (codepoint == ' ') {
        if (charsBefore && replace && codePointExistsAndNotSpace(value, codePointCount, index + 1)) {
          buf.append('-');
          charsBefore = false;
        }
      } else {
        buf.appendCodePoint(lowerCase ? Character.toLowerCase(codepoint) : codepoint);
        charsBefore = true;
      }
    }
    return buf.toString();
  }

  private static boolean codePointExistsAndNotSpace(String value, int count, int index) {
    return index < count && value.codePointAt(index) != ' ';
  }
}
