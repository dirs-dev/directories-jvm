package io.github.soc.directories;

import org.junit.Test;

public final class DirectoriesTest {
  @Test
  public void testBaseDirectories() {
    System.out.println("BaseDirectories:");
    System.out.println("  homeDir:       " + BaseDirectories.homeDir);
    System.out.println("  cacheDir:      " + BaseDirectories.cacheDir);
    System.out.println("  configDir:     " + BaseDirectories.configDir);
    System.out.println("  dataDir:       " + BaseDirectories.dataDir);
    System.out.println("  dataLocalDir:  " + BaseDirectories.dataLocalDir);
    System.out.println("  executableDir: " + BaseDirectories.executableDir);
    System.out.println("  runtimeDir:    " + BaseDirectories.runtimeDir);
  }

  @Test
  public void testUserDirectories() {
    System.out.println("UserDirectories:");
    System.out.println("  audioDir:    " + UserDirectories.audioDir);
    System.out.println("  desktopDir:  " + UserDirectories.desktopDir);
    System.out.println("  documentDir: " + UserDirectories.documentDir);
    System.out.println("  downloadDir: " + UserDirectories.downloadDir);
    System.out.println("  fontDir:     " + UserDirectories.fontDir);
    System.out.println("  pictureDir:  " + UserDirectories.pictureDir);
    System.out.println("  publicDir:   " + UserDirectories.publicDir);
    System.out.println("  templateDir: " + UserDirectories.templateDir);
    System.out.println("  videoDir:    " + UserDirectories.videoDir);
  }

  @Test
  public void testProjectDirectories() {
    ProjectDirectories projDirs = ProjectDirectories.from("org" /*qualifier*/, "Baz Corp" /*organization*/, "Foo Bar-App" /*project*/);
    System.out.println(projDirs);
  }
}
