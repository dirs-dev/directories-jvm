package io.github.soc.directories;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class UtilTest {

  @Test
  public void testStripQualification01() {
    final String input    = "org.foo.BarApp";
    final String actual   = Util.stripQualification(input);
    final String expected = "BarApp";
    assertEquals(input, expected, actual);
  }

  @Test
  public void testStripQualification02() {
    final String input    = "BarApp";
    final String actual   = Util.stripQualification(input);
    final String expected = "BarApp";
    assertEquals(input, expected, actual);
  }

  @Test
  public void testTrimLowercaseAndReplaceWhitespaces01() {
    final String input    = "Bar App";
    final String actual   = Util.trimLowercaseReplaceWhitespace(input, "-");
    final String expected = "bar-app";
    assertEquals(input, expected, actual);
  }

  @Test
  public void testTrimLowercaseAndReplaceWhitespaces02() {
    final String input    = "BarApp Foo";
    final String actual   = Util.trimLowercaseReplaceWhitespace(input, "");
    final String expected = "barappfoo";
    assertEquals(input, expected, actual);
  }

  @Test
  public void testTrimLowercaseAndReplaceWhitespaces03() {
    final String input    = " Bar App ";
    final String actual   = Util.trimLowercaseReplaceWhitespace(input, "-");
    final String expected = "bar-app";
    assertEquals(input, expected, actual);
  }

  @Test
  public void testTrimLowercaseAndReplaceWhitespaces04() {
    final String input    = "  Bar  App  ";
    final String actual   = Util.trimLowercaseReplaceWhitespace(input, "-");
    final String expected = "bar-app";
    assertEquals(input, expected, actual);
  }
}
