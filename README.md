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
| `cacheDir`        | `$XDG_CACHE_HOME`  or `~/.cache/`                                  | `{SpecialFolder.LocalApplicationData}/cache/` | `$HOME/Library/Caches/`              |
| `configDir`       | `$XDG_CONFIG_HOME` or `~/.config/`                                 | `{SpecialFolder.ApplicationData}`             | `$HOME/Library/Preferences/`         |
| `dataDir`         | `$XDG_DATA_HOME`   or `~/.local/share/`                            | `{SpecialFolder.ApplicationData}`             | `$HOME/Library/Application Support/` |
| `dataLocalDir`    | `$XDG_DATA_HOME`   or `~/.local/share/`                            | `{SpecialFolder.LocalApplicationData}`        | `$HOME/Library/Application Support/` |
| `executableDir`   | `$XDG_BIN_HOME` or `$XDG_DATA_HOME/../bin/` or `$HOME/.local/bin/` | `null`                                        | `null`                               |
| `runtimeDir`      | `$XDG_RUNTIME_DIR` or `null`                                       | `null`                                        | `null`                               |
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
which are derived from the standard directories.

| Instance field name | Value on Linux / BSD                                                | Value on Windows                                             | Value on macOS                                      |
| ------------------- | ------------------------------------------------------------------- | ------------------------------------------------------------ | --------------------------------------------------- |
| `cacheDir`          | `$XDG_CACHE_HOME/_project_path_` or `~/.cache/_project_path_/`      | `{SpecialFolder.LocalApplicationData}/_project_path_/cache/` | `$HOME/Library/Caches/_project_path_/`              |
| `configDir`         | `$XDG_CONFIG_HOME/_project_path_`  or `~/.config/_project_path_/`   | `{SpecialFolder.ApplicationData}/_project_path_/`            | `$HOME/Library/Preferences/_project_path_/`         |
| `dataDir`           | `$XDG_DATA_HOME/_project_path_` or `~/.local/share/_project_path_/` | `{SpecialFolder.ApplicationData}/_project_path_/`            | `$HOME/Library/Application Support/_project_path_/` |
| `dataLocalDir`      | `$XDG_DATA_HOME/_project_path_` or `~/.local/share/_project_path_/` | `{SpecialFolder.LocalApplicationData}/_project_path_/`       | `$HOME/Library/Application Support/_project_path_/` |
| `runtimeDir`        | `$XDG_RUNTIME_DIR/_project_path_`                                   | `null`                                                       | `null`                                              |

The specific value of `_project_path_` is computed by the

    ProjectDirectories.from(String qualifier, String organization, String project)

method and varies across operating systems. As an example, calling

    ProjectDirectories.from("org" /*qualifier*/, "Baz Corp" /*organization*/, "Foo Bar-App" /*project*/)

results in the following values:


| Value on Linux | Value on Windows         | Value on macOS               |
| -------------- | ------------------------ | ---------------------------- |
| `"foobar-app"` | `"Baz Corp/Foo Bar-App"` | `"org.Baz-Corp.Foo-Bar-App"` |

The `ProjectDirectories.fromPath` method allows the creation of `ProjectDirs` instances directly from a project path.
This argument is used verbatim and is not adapted to operating system standards.
The use of `ProjectDirectories.fromPath` is heavily discouraged, as its results will not follow operating system standards on at least two of three platforms.

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

`ProjectDirectories` factory methods have been overhauled and replaced with a single `from(String qualifier, String organization, String project)` method

- Changes to the selection of local/roaming data directories
  - `dataDir` and selects the roaming data directory on Windows (no change on Linux or macOS)
  - `dataLocalDir` and selects the local data directory on Windows (no change on Linux or macOS)

- `ProjectDirectories` received a `runtimeDir` field

- `ProjectDirectories` field names dropped the `project` prefix

- Changes to the directory for executables
  - Support for `executableDir` has been dropped on macOS
  - The value of `executableDir` considers `$XDG_BIN_HOME` now, before falling back to `$XDG_DATA_HOME/../bin` and `$HOME/.local/bin`

### 6

Current stable version.