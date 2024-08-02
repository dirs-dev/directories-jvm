package dev.dirs;

import dev.dirs.impl.Linux;
import dev.dirs.impl.Util;
import dev.dirs.impl.Windows;

/** {@code UserDirectories} provides paths of user-facing standard directories, following the conventions of the operating system the library is running on.
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
  public final String homeDir;

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
  public final String audioDir;

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
  public final String desktopDir;

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
  public final String documentDir;

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
  public final String downloadDir;

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
  public final String fontDir;

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
  public final String pictureDir;

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
  public final String publicDir;

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
  public final String templateDir;

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
  public final String videoDir;

  /** Creates a new {@code UserDirectories} instance.
    * <p>
    * The instance is an immutable snapshot of the state of the system at the time this method is invoked.
    * Subsequent changes to the state of the system are not reflected in instances created prior to such a change.
    *
    * @return A new {@code UserDirectories} instance.
    */
  public static UserDirectories get() {
     return new UserDirectories();
   }

  private UserDirectories() {
    switch (Constants.operatingSystem) {
      case Constants.LIN:
      case Constants.BSD:
      case Constants.SOLARIS:
      case Constants.AIX:
        String[] userDirs = Linux.getXDGUserDirs("MUSIC", "DESKTOP", "DOCUMENTS", "DOWNLOAD", "PICTURES", "PUBLICSHARE", "TEMPLATES", "VIDEOS");
        homeDir       = System.getProperty("user.home");
        audioDir      = userDirs[0];
        desktopDir    = userDirs[1];
        documentDir   = userDirs[2];
        downloadDir   = userDirs[3];
        fontDir       = Util.defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"), "/fonts",  homeDir, "/.local/share/fonts");
        pictureDir    = userDirs[4];
        publicDir     = userDirs[5];
        templateDir   = userDirs[6];
        videoDir      = userDirs[7];
        break;
      case Constants.MAC:
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
      case Constants.IBMI:
        homeDir       = System.getProperty("user.home");
        audioDir      = homeDir + "/Music";
        desktopDir    = homeDir + "/Desktop";
        documentDir   = homeDir + "/Documents";
        downloadDir   = homeDir + "/Downloads";
        fontDir       = Util.defaultIfNullOrEmptyExtended(System.getenv("XDG_DATA_HOME"), "/fonts",  homeDir, "/.local/share/fonts");
        pictureDir    = homeDir + "/Pictures";
        publicDir     = homeDir + "/Public";
        templateDir   = null;
        videoDir      = homeDir + "/Movies";
        break;
      case Constants.WIN:
        homeDir       = Windows.getProfileDir();
        audioDir      = Windows.getMusicDir();
        fontDir       = null;
        desktopDir    = Windows.getDesktopDir();
        documentDir   = Windows.getDocumentsDir();
        downloadDir   = Windows.getDownloadsDir();
        pictureDir    = Windows.getPicturesDir();
        publicDir     = Windows.getPublicDir();
        templateDir   = Windows.getTemplatesDir();
        videoDir      = Windows.getVideosDir();
        break;
      default:
        throw new UnsupportedOperatingSystemException("User directories are not supported on " + Constants.operatingSystemName);
    }
  }

  @Override
  public String toString() {
    return "UserDirectories (" + Constants.operatingSystemName + "):\n" +
        "  homeDir     = '" + homeDir     + "'\n" +
        "  audioDir    = '" + audioDir    + "'\n" +
        "  fontDir     = '" + fontDir     + "'\n" +
        "  desktopDir  = '" + desktopDir  + "'\n" +
        "  documentDir = '" + documentDir + "'\n" +
        "  downloadDir = '" + downloadDir + "'\n" +
        "  pictureDir  = '" + pictureDir  + "'\n" +
        "  publicDir   = '" + publicDir   + "'\n" +
        "  templateDir = '" + templateDir + "'\n" +
        "  videoDir    = '" + videoDir    + "'\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserDirectories that = (UserDirectories) o;

    if (homeDir     != null ? !homeDir    .equals(that.homeDir)     : that.homeDir     != null)
      return false;
    if (audioDir    != null ? !audioDir   .equals(that.audioDir)    : that.audioDir    != null)
      return false;
    if (fontDir     != null ? !fontDir    .equals(that.fontDir)     : that.fontDir     != null)
      return false;
    if (desktopDir  != null ? !desktopDir .equals(that.desktopDir)  : that.desktopDir  != null)
      return false;
    if (documentDir != null ? !documentDir.equals(that.documentDir) : that.documentDir != null)
      return false;
    if (downloadDir != null ? !downloadDir.equals(that.downloadDir) : that.downloadDir != null)
      return false;
    if (pictureDir  != null ? !pictureDir .equals(that.pictureDir)  : that.pictureDir  != null)
      return false;
    if (publicDir   != null ? !publicDir  .equals(that.publicDir)   : that.publicDir   != null)
      return false;
    if (templateDir != null ? !templateDir.equals(that.templateDir) : that.templateDir != null)
      return false;
    if (videoDir    != null ? !videoDir   .equals(that.videoDir)    : that.videoDir    != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = 0;
    result = 31 * result + (homeDir     != null ? homeDir    .hashCode() : 0);
    result = 31 * result + (audioDir    != null ? audioDir   .hashCode() : 0);
    result = 31 * result + (fontDir     != null ? fontDir    .hashCode() : 0);
    result = 31 * result + (desktopDir  != null ? desktopDir .hashCode() : 0);
    result = 31 * result + (documentDir != null ? documentDir.hashCode() : 0);
    result = 31 * result + (downloadDir != null ? downloadDir.hashCode() : 0);
    result = 31 * result + (pictureDir  != null ? pictureDir .hashCode() : 0);
    result = 31 * result + (publicDir   != null ? publicDir  .hashCode() : 0);
    result = 31 * result + (templateDir != null ? templateDir.hashCode() : 0);
    result = 31 * result + (videoDir    != null ? videoDir   .hashCode() : 0);
    return result;
  }
}
