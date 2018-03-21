package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/** <code>UserDirectories</code> provides paths of user-facing standard directories, following the conventions of the operating system the library is running on.
  * <p>
  * It is a snapshot of the state of the system at the time this class is initialized.
  *
  * <h2>Examples</h2>
  *
  * All examples on this page are computed with a user named <em>Alice</em>.
  * <p>
  * <code style="white-space: pre">
  * UserDirectories.audioDir;
  * // Linux:   /home/alice/Music
  * // Windows: /Users/Alice/Music
  * // macOS:   C:\Users\Alice\Music
  * </code>
  */
public final class UserDirectories {

  private UserDirectories() {
    throw new Error();
  }
  /** Returns the path to the user's home directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                | Example        |
    * | ------- | -------------------- | -------------- |
    * | Linux   | `$HOME`              | /home/alice    |
    * | macOS   | `$HOME`              | /Users/Alice   |
    * | Windows | `{FOLDERID_Profile}` | C:\Users\Alice |
    * </code>
    */
  public static final String homeDir;

  // user directories

  /** Returns the path to the user's audio directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value              | Example              |
    * | ------- | ------------------ | -------------------- |
    * | Linux   | `XDG_MUSIC_DIR`    | /home/alice/Music    |
    * | macOS   | `$HOME`/Music      | /Users/Alice/Music   |
    * | Windows | `{FOLDERID_Music}` | C:\Users\Alice\Music |
    * </code>
    */
  public static final String audioDir;
  /** Returns the path to the user's desktop directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                | Example                |
    * | ------- | -------------------- | ---------------------- |
    * | Linux   | `XDG_DESKTOP_DIR`    | /home/alice/Desktop    |
    * | macOS   | `$HOME`/Desktop      | /Users/Alice/Desktop   |
    * | Windows | `{FOLDERID_Desktop}` | C:\Users\Alice\Desktop |
    * </code>
    */
  public static final String desktopDir;
  /** Returns the path to the user's document directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                  | Example                  |
    * | ------- | ---------------------- | ------------------------ |
    * | Linux   | `XDG_DOCUMENTS_DIR`    | /home/alice/Documents    |
    * | macOS   | `$HOME`/Documents      | /Users/Alice/Documents   |
    * | Windows | `{FOLDERID_Documents}` | C:\Users\Alice\Documents |
    * </code>
    */
  public static final String documentDir;
  /** Returns the path to the user's download directory.
    * <p>
    * Please note that this value is not available on Windows, 
    * as the <code>SpecialFolder</code> enumeration is missing such an entry.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                  | Example                  |
    * | ------- | ---------------------- | ------------------------ |
    * | Linux   | `XDG_DOWNLOAD_DIR`     | /home/alice/Downloads    |
    * | macOS   | `$HOME`/Downloads      | /Users/Alice/Downloads   |
    * | Windows | –                      | null                     |
    * </code>
    */
  public static final String downloadDir;
  /** Returns the path to the user's font directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                                                | Example                        |
    * | ------- | ---------------------------------------------------- | ------------------------------ |
    * | Linux   | `$XDG_DATA_HOME`/fonts or `$HOME`/.local/share/fonts | /home/alice/.local/share/fonts |
    * | macOS   | `$HOME`/Library/Fonts                                | /Users/Alice/Library/Fonts     |
    * | Windows | null                                                 | null                           |
    * </code>
    */
  public static final String fontDir;
  /** Returns the path to the user's picture directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                 | Example                 |
    * | ------- | --------------------- | ----------------------- |
    * | Linux   | `XDG_PICTURES_DIR`    | /home/alice/Pictures    |
    * | macOS   | `$HOME`/Pictures      | /Users/Alice/Pictures   |
    * | Windows | `{FOLDERID_Pictures}` | C:\Users\Alice\Pictures |
    * </code>
    */
  public static final String pictureDir;
  /** Returns the path to the user's public directory.
    * <p>
    * Please note that this value is not available on Windows, 
    * as the <code>SpecialFolder</code> enumeration is missing such an entry.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                 | Example             |
    * | ------- | --------------------- | ------------------- |
    * | Linux   | `XDG_PUBLICSHARE_DIR` | /home/alice/Public  |
    * | macOS   | `$HOME`/Public        | /Users/Alice/Public |
    * | Windows | –                     | null                |
    * </code>
    */
  public static final String publicDir;
  /** Returns the path to the user's template directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value                  | Example                                                    |
    * | ------- | ---------------------- | ---------------------------------------------------------- |
    * | Linux   | `XDG_TEMPLATES_DIR`    | /home/alice/Templates                                      |
    * | macOS   | null                   | null                                                       |
    * | Windows | `{FOLDERID_Templates}` | C:\Users\Alice\AppData\Roaming\Microsoft\Windows\Templates |
    * </code>
    */
  public static final String templateDir;
  /** Returns the path to the user's video directory.
    * <p>
    * <code style="white-space: pre">
    * |Platform | Value               | Example               |
    * | ------- | ------------------- | --------------------- |
    * | Linux   | `XDG_VIDEOS_DIR`    | /home/alice/Videos    |
    * | macOS   | `$HOME`/Movies      | /Users/Alice/Movies   |
    * | Windows | `{FOLDERID_Videos}` | C:\Users\Alice\Videos |
    * </code>
    */
  public static final String videoDir;

  static {
    switch (operatingSystem) {
      case LIN:
      case BSD:
        homeDir       = System.getProperty("user.home");
        audioDir      = runXDGUserDir("MUSIC");
        desktopDir    = runXDGUserDir("DESKTOP");
        documentDir   = runXDGUserDir("DOCUMENTS");
        downloadDir   = runXDGUserDir("DOWNLOAD");
        fontDir       = defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"), "/fonts",  homeDir, "/.local/share/fonts");
        pictureDir    = runXDGUserDir("PICTURES");
        publicDir     = runXDGUserDir("PUBLICSHARE");
        templateDir   = runXDGUserDir("TEMPLATES");
        videoDir      = runXDGUserDir("VIDEOS");
        break;
      case MAC:
        homeDir       = System.getProperty("user.home");
        audioDir      = homeDir + "/Music";
        desktopDir    = homeDir + "/Desktop";
        documentDir   = homeDir + "/Documents";
        downloadDir   = homeDir + "/Downloads";
        fontDir       = homeDir + "/Library/Fonts";
        pictureDir    = homeDir + "/Pictures";
        publicDir     = homeDir + "/Public";
        templateDir   = null;
        videoDir      = homeDir + "/Movies";
        break;
      case WIN:
        homeDir       = runPowerShellCommand("UserProfile");
        audioDir      = runPowerShellCommand("MyMusic");
        fontDir       = null;
        desktopDir    = runPowerShellCommand("DesktopDirectory");
        documentDir   = runPowerShellCommand("MyDocuments");
        downloadDir   = null;
        pictureDir    = runPowerShellCommand("MyPictures");
        publicDir     = null;
        templateDir   = runPowerShellCommand("Templates");
        videoDir      = runPowerShellCommand("MyVideos");
        break;
      default:
        throw new UnsupportedOperatingSystemException("User directories are not supported on " + operatingSystemName);
    }
  }
}
