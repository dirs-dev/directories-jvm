package dev.dirs;

import org.junit.Test;

public final class DirectoriesTest {

  @Test
  public void testBaseDirectories() {
    BaseDirectories baseDirs = BaseDirectories.get();
    System.out.println(baseDirs);
  }

  @Test
  public void testUserDirectories() {
    UserDirectories userDirs = UserDirectories.get();
    System.out.println(userDirs);
  }

  @Test
  public void testProjectDirectories() {
    ProjectDirectories projDirs = ProjectDirectories.from("org" /*qualifier*/, "Baz Corp" /*organization*/, "Foo Bar-App" /*project*/);
    System.out.println(projDirs);
  }
}
