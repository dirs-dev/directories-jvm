package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/**
 * {@code UserDirectories} provides paths of user-facing standard directories, following the conventions of the operating system the library is running on.
 * <p>
 * It is a snapshot of the state of the system at the time this class is initialized.
 *
 * <h2>Examples</h2>
 * <p>
 * All examples on this page are computed with a user named <em>Alice</em>.
 * <p>
 * Example of {@link UserDirectories#audioDir} value in different operating systems:
 * <ul>
 * <li><b>Linux / BSD:</b> {@code /home/alice/Music}</li>
 * <li><b>macOS:</b> {@code /Users/Alice/Music}</li>
 * <li><b>Windows:</b> {@code C:\Users\Alice\Music}</li>
 * </ul>
 */
public final class UserDirectories {

  private UserDirectories() {
    throw new Error();
  }

  /**
   * Returns the path to the user's home directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code $HOME}</td>
   * <td>/home/alice</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}</td>
   * <td>/Users/Alice</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Profile}}</td>
   * <td>C:\Users\Alice</td>
   * </tr>
   * </table>
   */
  public static final String homeDir;

  /**
   * Returns the path to the user's audio directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_MUSIC_DIR}</td>
   * <td>/home/alice/Music</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Music</td>
   * <td>/Users/Alice/Music</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Music}}</td>
   * <td>C:\Users\Alice\Music</td>
   * </tr>
   * </table>
   */
  public static final String audioDir;

  /**
   * Returns the path to the user's desktop directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_DESKTOP_DIR}</td>
   * <td>/home/alice/Desktop</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Desktop</td>
   * <td>/Users/Alice/Desktop</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Desktop}}</td>
   * <td>C:\Users\Alice\Desktop</td>
   * </tr>
   * </table>
   */
  public static final String desktopDir;

  /**
   * Returns the path to the user's document directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_DOCUMENTS_DIR}</td>
   * <td>/home/alice/Documents</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Documents</td>
   * <td>/Users/Alice/Documents</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Documents}}</td>
   * <td>C:\Users\Alice\Documents</td>
   * </tr>
   * </table>
   */
  public static final String documentDir;

  /**
   * Returns the path to the user's download directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_DOWNLOAD_DIR}</td>
   * <td>/home/alice/Downloads</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Downloads</td>
   * <td>/Users/Alice/Downloads</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * </table>
   */
  public static final String downloadDir;

  /**
   * Returns the path to the user's font directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code $XDG_DATA_HOME}/fonts or {@code $HOME}/.local/share/fonts</td>
   * <td>/home/alice/.local/share/fonts</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Library/Fonts</td>
   * <td>/Users/Alice/Library/Fonts</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * </table>
   */
  public static final String fontDir;

  /**
   * Returns the path to the user's picture directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_PICTURES_DIR}</td>
   * <td>/home/alice/Pictures</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Pictures</td>
   * <td>/Users/Alice/Pictures</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Pictures}}</td>
   * <td>C:\Users\Alice\Pictures</td>
   * </tr>
   * </table>
   */
  public static final String pictureDir;

  /**
   * Returns the path to the user's public directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_PUBLICSHARE_DIR}</td>
   * <td>/home/alice/Public</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Public</td>
   * <td>/Users/Alice/Public</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * </table>
   */
  public static final String publicDir;

  /**
   * Returns the path to the user's template directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_TEMPLATES_DIR}</td>
   * <td>/home/alice/Templates</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>-</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Templates}}</td>
   * <td>C:\Users\Alice\AppData\Roaming\Microsoft\Windows\Templates</td>
   * </tr>
   * </table>
   */
  public static final String templateDir;

  /**
   * Returns the path to the user's video directory.
   *
   * <br><br>
   * <table border="1" cellpadding="1" cellspacing="0">
   * <tr>
   * <th align="left">Platform</th>
   * <th align="left">Value</th>
   * <th align="left">Example</th>
   * </tr>
   * <tr>
   * <td>Linux</td>
   * <td>{@code XDG_VIDEOS_DIR}</td>
   * <td>/home/alice/Videos</td>
   * </tr>
   * <tr>
   * <td>macOS</td>
   * <td>{@code $HOME}/Movies</td>
   * <td>/Users/Alice/Movies</td>
   * </tr>
   * <tr>
   * <td>Windows</td>
   * <td>{@code {FOLDERID_Videos}}</td>
   * <td>C:\Users\Alice\Videos</td>
   * </tr>
   * </table>
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
