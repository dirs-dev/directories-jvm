package dev.dirs.cli;

import dev.dirs.ProjectDirectories;
import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(name = "directories", mixinStandardHelpOptions = true, version = "20")
public class DirectoriesCli implements Runnable {
  @Option(names = {"--cache"}, description = "Prints cache directory")
  boolean cache;
  @Option(names = {"--config"}, description = "Prints config directory")
  boolean config;
  @Option(names = {"--data"}, description = "Prints data directory")
  boolean data;
  @Option(names = {"--data-local"}, description = "Prints local data directory")
  boolean dataLocal;
  @Option(names = {"--preferences"}, description = "Prints preferences directory")
  boolean preferences;
  @Option(names = {"--runtime"}, description = "Prints runtime directory")
  boolean runtime;

  @Parameters(index = "0")
  String project;

  @Override
  public void run() {
    int dirCount = 0;
    if (cache) dirCount += 1;
    if (config) dirCount += 1;
    if (data) dirCount += 1;
    if (dataLocal) dirCount += 1;
    if (preferences) dirCount += 1;
    if (runtime) dirCount += 1;

    boolean all = dirCount == 0;

    ProjectDirectories projDirs = ProjectDirectories.from(null, null, project);

    if (dirCount == 1) {
      if (cache)
        System.out.println(projDirs.cacheDir);
      if (config)
        System.out.println(projDirs.configDir);
      if (data)
        System.out.println(projDirs.dataDir);
      if (dataLocal)
        System.out.println(projDirs.dataLocalDir);
      if (preferences)
        System.out.println(projDirs.preferenceDir);
      if (runtime)
        System.out.println(projDirs.runtimeDir);
    } else {
      if (all || cache)
        System.out.println("cache:      " + projDirs.cacheDir);
      if (all || config)
        System.out.println("config:     " + projDirs.configDir);
      if (all || data)
        System.out.println("data:       " + projDirs.dataDir);
      if (all || dataLocal)
        System.out.println("local data: " + projDirs.dataLocalDir);
      if (all || preferences)
        System.out.println("preference: " + projDirs.preferenceDir);
      if (all || runtime)
        System.out.println("runtime:    " + projDirs.runtimeDir);
    }
  }

  public static void main(String... args) {
    System.exit(new CommandLine(new DirectoriesCli()).execute(args));
  }
}
