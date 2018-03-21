package io.github.soc.directories;

import        org.junit.Test;
import static org.junit.Assert.assertEquals;

public final class UtilTest {
  @Test
  public void testStripQualification() {
    String actual1   = Util.stripQualification("org.foo.BarApp");
    String expected1 = "BarApp";
    assertEquals(actual1, expected1);

    String actual2   = Util.stripQualification("BarApp");
    String expected2 = "BarApp";
    assertEquals(actual2, expected2);
  }

  @Test

  public void testTrimLowercaseAndReplaceWhitespaces1() {
    String input1    = "Bar App";
    String actual1   = Util.trimLowercaseReplaceWhitespace(input1, "-");
    String expected1 = "bar-app";
    assertEquals(input1,  expected1, actual1);
  }

  public void testTrimLowercaseAndReplaceWhitespaces2() {
    String input2    = "BarApp Foo";
    String actual2   = Util.trimLowercaseReplaceWhitespace(input2, "");
    String expected2 = "barappfoo";
    assertEquals(input2, expected2, actual2);
  }

  public void testTrimLowercaseAndReplaceWhitespaces3() {
    String input3    = " Bar App ";
    String actual3   = Util.trimLowercaseReplaceWhitespace(input3, "-");
    String expected3 = "bar-app";
    assertEquals(input3, expected3, actual3);
  }

  public void testTrimLowercaseAndReplaceWhitespaces4() {
    String input4    = "  Bar  App  ";
    String actual4   = Util.trimLowercaseReplaceWhitespace(input4, "-");
    String expected4 = "bar-app";
    assertEquals(input4, expected4, actual4);
  }
}
