# Directories

## Introduction

- A tiny library (8kB)
- with minimal public API (3 classes, 6 methods, 21 fields)
- for convenient access to standardized directories
- on Linux, Windows (≥ 7), macOS and BSD
- running on the JVM

The library provides the location of directories by leveraging the mechanisms defined by
- the [XDG base directory](https://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html) and
  the [XDG user directory](https://www.freedesktop.org/wiki/Software/xdg-user-dirs/) specifications on Linux,
- the [SpecialFolder](https://msdn.microsoft.com/en-us/library/system.environment.specialfolder.aspx) enumeration on Windows, and
- the [Standard Directories](https://developer.apple.com/library/content/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/FileSystemOverview/FileSystemOverview.html#//apple_ref/doc/uid/TP40010672-CH2-SW6)
  on macOS.

## Usage

#### Dependency

Add the library as a dependency to your project:

##### Maven
```xml
<dependency>
  <groupId>io.github.soc</groupId>
  <artifactId>directories</artifactId>
  <version>6</version>
</dependency>
```
##### Gradle
```groovy
compile 'io.github.soc:directories:6'
```

##### SBT
```scala
"io.github.soc" % "directories" % "6"
```

#### Example

Library run by a user with user name "my_user_name" on Linux:

```java
import io.github.soc.directories.ProjectDirectories;
ProjectDirectories myProjDirs      = ProjectDirectories.fromProjectName("My Project");
String             myProjConfigDir = myProjDirs.projectConfigDir;
System.out.println(myProjConfigDir); // "/home/my_user_name/.config/my-project/"
```

## Features

### `BaseDirectories`

The intended use-case for `BaseDirectories` is to query the paths of standard folders
that have been defined according to the conventions of operating system the library is running on.

If you want to compute the location of cache, config or data folders for your own application or project, use `ProjectDirectories` instead.

| Static field name | Value on Linux / BSD                                               | Value on Windows                              | Value on macOS                       |
| ----------------- | ------------------------------------------------------------------ | --------------------------------------------- | ------------------------------------ |
| `homeDir`         | `$HOME`                                                            | `{SpecialFolder.UserProfile}`                 | `$HOME`                              |
| `cacheDir`        | `$XDG_CACHE_DIR`  or `~/.cache/`                                   | `{SpecialFolder.LocalApplicationData}/cache/` | `$HOME/Library/Caches/`              |
| `configDir`       | `$XDG_CONFIG_DIR` or `~/.config/`                                  | `{SpecialFolder.ApplicationData}`             | `$HOME/Library/Preferences/`         |
| `dataDir`         | `$XDG_DATA_DIR`   or `~/.local/share/`                             | `{SpecialFolder.ApplicationData}`             | `$HOME/Library/Application Support/` |
| `dataLocalDir`    | `$XDG_DATA_DIR`   or `~/.local/share/`                             | `{SpecialFolder.LocalApplicationData}`        | `$HOME/Library/Application Support/` |
| `executableDir`   | `$XDG_BIN_HOME` or `$XDG_DATA_HOME/../bin/` or `$HOME/.local/bin/` | `null`                                        | `null`                               |
| `runtimeDir`      | `$XDG_RUNTIME_DIR`                                                 | `null`                                        | `null`                               |
| `audioDir`        | `XDG_MUSIC_DIR`                                                    | `{SpecialFolder.Music}`                       | `$HOME/Music/`                       |
| `desktopDir`      | `XDG_DESKTOP_DIR`                                                  | `{SpecialFolder.Desktop}`                     | `$HOME/Desktop/`                     |
| `documentDir`     | `XDG_DOCUMENTS_DIR`                                                | `{SpecialFolder.Documents}`                   | `$HOME/Documents/`                   |
| `downloadDir`     | `XDG_DOWNLOAD_DIR`                                                 | `{SpecialFolder.Downloads}`                   | `$HOME/Downloads/`                   |
| `fontDir`         | `$XDG_DATA_HOME/fonts/` or `/.local/share/fonts/`                  | `null`                                        | `$HOME/Library/Fonts/`               |
| `pictureDir`      | `XDG_PICTURES_DIR`                                                 | `{SpecialFolder.Pictures}`                    | `$HOME/Pictures/`                    |
| `publicDir`       | `XDG_PUBLICSHARE_DIR`                                              | `{SpecialFolder.Public}`                      | `$HOME/Public/`                      |
| `templateDir`     | `XDG_TEMPLATES_DIR`                                                | `{SpecialFolder.Templates}`                   | `null`                               |
| `videoDir`        | `XDG_VIDEOS_DIR`                                                   | `{SpecialFolder.Videos}`                      | `$HOME/Movies/`                      |

### `ProjectDirectories`

The intended use-case for `BaseDirectories` is to compute the location of cache, config or data folders for your own application or project,
which are derived from the standardized directories.

| Instance field name   | Value on Linux / BSD                                                     | Value on Windows                                                | Value on macOS                                         |
| --------------------- | ------------------------------------------------------------------------ | --------------------------------------------------------------- | ------------------------------------------------------ |
| `projectCacheDir`     | `$XDG_CACHE_DIR/_yourprojectname_` or `~/.cache/_yourprojectname_/`      | `{SpecialFolder.LocalApplicationData}/_yourprojectname_/cache/` | `$HOME/Library/Caches/_yourprojectname_/`              |
| `projectConfigDir`    | `$XDG_CONFIG_DIR/_yourprojectname_`  or `~/.config/_yourprojectname_/`   | `{SpecialFolder.ApplicationData}/_yourprojectname_/`            | `$HOME/Library/Preferences/_yourprojectname_/`         |
| `projectDataDir`      | `$XDG_DATA_DIR/_yourprojectname_` or `~/.local/share/_yourprojectname_/` | `{SpecialFolder.ApplicationData}/_yourprojectname_/`            | `$HOME/Library/Application Support/_yourprojectname_/` |
| `projectDataLocalDir` | `$XDG_DATA_DIR/_yourprojectname_` or `~/.local/share/_yourprojectname_/` | `{SpecialFolder.LocalApplicationData}/_yourprojectname_/`       | `$HOME/Library/Application Support/_yourprojectname_/` |

The specific value of `_yourprojectname_` depends on the factory method used to create a `ProjectDirectories` instance:

| Factory method              | Example project name          | Value on Linux | Value on Windows | Value on macOS                |
| --------------------------- | ----------------------------- | -------------- | ---------------- | ----------------------------- |
| `fromUnprocessedString`     | `"FooBar-App"`                | `"FooBar-App"` | `"FooBar-App"`   | `"FooBar-App"`                |
| `fromProjectName`           | `"FooBar App"`                | `"foobar-app"` | `"FooBar App"`   | `"FooBar App"`                |
| `fromQualifiedProjectName`  | `"org.foobar-corp.FooBarApp"` | `"foobarapp"`  | `"FooBarApp"`    | `"org.foobar-corp.FooBarApp"` |

## Versioning

The version number of this library consists of a whole number, which is incremented with each release.
(Think semantic versioning without _minor_ and _patch_ versions.)

## Changelog

### 7-pre

- More consistent and intuitive naming scheme (all names are singular now)

| Old name                | New name              |
| ----------------------- | --------------------- |
| `dataDir`               | `dataLocalDir`        |
| `dataRoamingDir`        | `dataDir`             |
| `executablesDir`        | `executableDir`       |
| `documentsDir`          | `documentDir`         |
| `downloadsDir`          | `downloadDir`         |
| `fontsDir`              | `fontDir`             |
| `musicDir`              | `audioDir`            |
| `picturesDir`           | `pictureDir`          |
| `templatesDir`          | `templateDir`         |
| `videosDir`             | `videoDir`            |
| `projectDataDir`        | `projectDataLocalDir` |
| `projectDataRoamingDir` | `projectDataDir`      |

- Changes to the selection of local/roaming data directories
  - `dataDir` and `projectDataDir` selects the roaming data directory on Windows (no change on Linux or macOS)
  - `dataLocalDir` and `projectDataLocalDir` selects the local data directory on Windows (no change on Linux or macOS)

- Changes to the directory for executables
  - Support for `executableDir` has been dropped on macOS
  - The value of `executableDir` considers `$XDG_BIN_HOME` now, before falling back to `$XDG_DATA_HOME/../bin` and `$HOME/.local/bin`

### 6

Current stable version.