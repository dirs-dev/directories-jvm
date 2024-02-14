package dev.dirs;

public interface GetWinDirs {
    String[] getWinDirs(String... guids);

    GetWinDirs powerShellBased = Util::getWinDirs;
}
