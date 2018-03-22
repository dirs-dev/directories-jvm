package io.github.soc.directories;

import static io.github.soc.directories.Util.*;

/** {@code UserDirectories} provides paths of user-facing standard directories, following the conventions of the operating system the library is running on.
  * <p>
  * It is a snapshot of the state of the system at the time this class is initialized.
  *
  * <h2>Examples</h2>
  * <p>
  * All examples on this page are computed with a user named <em>Alice</em>.
  * <p>
  * Example of {@link UserDirectories#audioDir} value in different operating systems:
  * <ul>
  * <li><b>Linux/BSD:</b> {@code /home/alice/Music}</li>
  * <li><b>macOS:</b> {@code /Users/Alice/Music}</li>
  * <li><b>Windows:</b> {@code C:\Users\Alice\Music}</li>
  * </ul>
  */
public final class UserDirectories {

  private UserDirectories() {
    throw new Error();
  }

  /** Returns the path to the user's home directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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

  /** Returns the path to the user's audio directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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

  /** Returns the path to the user's desktop directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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

  /** Returns the path to the user's document directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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

  /** Returns the path to the user's download directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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
    * <td>{@code {FOLDERID_Downloads}}</td>
    * <td>C:\Users\Alice\Downloads</td>
    * </tr>
    * </table>
    */
  public static final String downloadDir;

  /** Returns the path to the user's font directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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
    * <td>–</td>
    * <td>{@code null}</td>
    * </tr>
    * </table>
    */
  public static final String fontDir;

  /** Returns the path to the user's picture directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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

  /** Returns the path to the user's public directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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
    * <td>{@code {FOLDERID_Public}}</td>
    * <td>C:\Users\Public</td>
    * </tr>
    * </table>
    */
  public static final String publicDir;

  /** Returns the path to the user's template directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
    * <td>{@code XDG_TEMPLATES_DIR}</td>
    * <td>/home/alice/Templates</td>
    * </tr>
    * <tr>
    * <td>macOS</td>
    * <td>–</td>
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

  /** Returns the path to the user's video directory.
    * <br><br>
    * <table border="1" cellpadding="1" cellspacing="0">
    * <tr>
    * <th align="left">Platform</th>
    * <th align="left">Value</th>
    * <th align="left">Example</th>
    * </tr>
    * <tr>
    * <td>Linux/BSD</td>
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
        String[] userDirs = getXDGUserDirs("MUSIC", "DESKTOP", "DOCUMENTS", "DOWNLOAD", "PICTURES", "PUBLICSHARE", "TEMPLATES", "VIDEOS");
        homeDir       = System.getProperty("user.home");
        audioDir      = userDirs[0];
        desktopDir    = userDirs[1];
        documentDir   = userDirs[2];
        downloadDir   = userDirs[3];
        fontDir       = defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"), "/fonts",  homeDir, "/.local/share/fonts");
        pictureDir    = userDirs[4];
        publicDir     = userDirs[5];
        templateDir   = userDirs[6];
        videoDir      = userDirs[7];
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
        String[] winDirs = getWinDirs(
            "5E6C858F-0E22-4760-9AFE-EA3317B67173",
            "4BD8D571-6D19-48D3-BE97-422220080E43",
            "B4BFCC3A-DB2C-424C-B029-7FE99A87C641",
            "FDD39AD0-238F-46AF-ADB4-6C85480369C7",
            "374DE290-123F-4565-9164-39C4925E467B",
            "33E28130-4E1E-4676-835A-98395C3BC3BB",
            "DFDF76A2-C82A-4D63-906A-5644AC457385",
            "A63293E8-664E-48DB-A079-DF759E0509F7",
            "18989B1D-99B5-455B-841C-AB7C74E4DDFC");
        homeDir       = winDirs[0];
        audioDir      = winDirs[1];
        fontDir       = null;
        desktopDir    = winDirs[2];
        documentDir   = winDirs[3];
        downloadDir   = winDirs[4];
        pictureDir    = winDirs[5];
        publicDir     = winDirs[6];
        templateDir   = winDirs[7];
        videoDir      = winDirs[8];
        break;
      default:
        throw new UnsupportedOperatingSystemException("User directories are not supported on " + operatingSystemName);
    }
  }
}
