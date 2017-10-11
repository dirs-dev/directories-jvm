package io.github.soc.directories;

public final class MacBaseDirectories extends BaseDirectories {
	
	public MacBaseDirectories(String projectName) {

		this.homeDir        = System.getenv("HOME");

		this.cacheDir       = homeDir + "/Library/Caches/";
		this.configDir      = homeDir + "/Library/Preferences/";
		this.dataDir        = homeDir + "/Library/Application Support/";
		this.runtimeDir     = null;
                            
		this.desktopDir     = homeDir + "/Desktop/";
		this.documentsDir   = homeDir + "/Documents/";
		this.downloadDir    = homeDir + "/Downloads/";
		this.musicDir       = homeDir + "/Music/";
		this.picturesDir    = homeDir + "/Pictures/";
		this.publicDir      = null;
		this.templatesDir   = null;
		this.videosDir      = homeDir + "/Movies/";

		this.executablesDir = null;
		this.fontsDir       = homeDir + "/Library/Fonts/";
	}
}
