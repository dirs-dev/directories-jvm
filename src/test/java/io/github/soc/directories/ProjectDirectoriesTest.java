package io.github.soc.directories;

import        org.junit.Test;
import static org.junit.Assert.assertEquals;

final class ProjectDirectoriesTest {
  @Test
  void testProjectDirectoriesFrom() {
    ProjectDirectories projDirs = ProjectDirectories.from("org" /*qualifier*/, "Baz Corp" /*organization*/, "Foo Bar-App" /*project*/);
    System.out.println(projDirs);
  }
}