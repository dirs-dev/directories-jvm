package io.github.soc.directories;

public abstract class BaseDirectories {
   
	protected String homeDir;

	// xdg base directories
	protected String cacheDir;
	protected String configDir;
	protected String dataDir;
	protected String runtimeDir;

	// xdg user directories
	protected String desktopDir;
	protected String documentsDir;
	protected String downloadDir;
	protected String musicDir;
	protected String picturesDir;
	protected String publicDir;
	protected String templatesDir;
	protected String videosDir;

	// derived
	protected String executablesDir;
	protected String fontsDir;
}
