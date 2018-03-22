# Directories

## Introduction

- a tiny library (9kB) with a minimal API
- that provides the platform-specific, user-accessible locations
- for retrieving and storing configuration, cache and other data
- on Linux, Windows (≥ 7), macOS and BSD

The library provides the location of directories by leveraging the mechanisms defined by
- The [XDG base directory](https://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html) and
  the [XDG user directory](https://www.freedesktop.org/wiki/Software/xdg-user-dirs/) specifications on Linux.
- The [KNOWNFOLDERID](https://msdn.microsoft.com/en-us/library/windows/desktop/dd378457(v=vs.85).aspx) GUIDs on Windows.
- The [Standard Directories](https://developer.apple.com/library/content/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/FileSystemOverview/FileSystemOverview.html#//apple_ref/doc/uid/TP40010672-CH2-SW6)
  guidelines on macOS.

## Platforms

This library is written in Java, and runs on the JVM (≥ 6).

A version of this library implemented in Rust is provided by [directories-rs](https://github.com/soc/directories-rs).

## Usage

#### Dependency

Add the library as a dependency to your project:

##### Maven
```xml
<dependency>
  <groupId>io.github.soc</groupId>
  <artifactId>directories</artifactId>
  <version>10</version>
</dependency>
```
##### Gradle
```groovy
compile 'io.github.soc:directories:10'
```

##### SBT
```scala
"io.github.soc" % "directories" % "10"
```

The library itself is built against Java 6 to allow for the widest possible usage scenarios.
It can be used on any Java version >= 6.

#### Example

Library run by user Alice:

```java
import io.github.soc.directories.ProjectDirectories;

ProjectDirectories myProjDirs = ProjectDirectories.from("com", "Foo Corp", "Bar App");
myProjDirs.configDir;
// Lin: /home/alice/.config/barapp
// Mac: /Users/Alice/Library/Preferences/com.Foo-Corp.Bar-App
// Win: C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App\config

BaseDirectories.executableDir;
// Lin: /home/alice/.local/bin
// Mac: null
// Win: null

UserDirs.audioDir;
// Lin: /home/alice/Music
// Mac: /Users/Alice/Music
// Win: C:\Users\Alice\Music
```

## Design Goals

- The _directories_ library is designed to provide an accurate snapshot of the system's state at
  the point of initialization/invocation of `BaseDirectories`, `UserDirectories` or
  `ProjectDirectories::from`. Subsequent changes to the state of the system are not reflected in
  values creates prior to such a change.
- This library does not create directories or check for their existence. The library only provides
  information on what the path to a certain directory _should_ be. How this information is used is
  a decision that developers need to make based on the requirements of each individual application.
- This library is intentionally focused on providing information on user-writable directories only.
  There is no discernible benefit in returning a path that points to a user-level, writable
  directory on one operating system, but a system-level, read-only directory on another, that would
  outweigh the confusion and unexpected failures such an approach would cause.
  - `executableDir` is specified to provide the path to a user-writable directory for binaries.<br/>
    As such a directory only commonly exists on Linux, it returns `None` on macOS and Windows.
  - `fontDir` is specified to provide the path to a user-writable directory for fonts.<br/>
    As such a directory only exists on Linux and macOS, it returns `None` Windows.
  - `runtimeDir` is specified to provide the path to a directory for non-essential runtime data.
    It is required that this directory is created when the user logs in, is only accessible by the
    user itself, is deleted when the user logs out, and supports all filesystem features of the
    operating system.<br/>
    As such a directory only commonly exists on Linux, it returns `None` on macOS and Windows.

## Features

### `BaseDirectories`

The intended use-case for `BaseDirectories` is to query the paths of user-invisible standard directories
that have been defined according to the conventions of the operating system the library is running on.

If you want to compute the location of cache, config or data folders for your own application or project, use `ProjectDirectories` instead.

| Static field name | Value on Linux / BSD                                             | Value on Windows                         | Value on macOS                      |
| ----------------- | ---------------------------------------------------------------- | ---------------------------------------- | ----------------------------------- |
| `homeDir`         | `$HOME`                                                          | `{5E6C858F-0E22-4760-9AFE-EA3317B67173}` | `$HOME`                             |
| `cacheDir`        | `$XDG_CACHE_HOME`  or `$HOME/.cache`                             | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}` | `$HOME/Library/Caches`              |
| `configDir`       | `$XDG_CONFIG_HOME` or `$HOME/.config`                            | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}` | `$HOME/Library/Preferences`         |
| `dataDir`         | `$XDG_DATA_HOME`   or `$HOME/.local/share`                       | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}` | `$HOME/Library/Application Support` |
| `dataLocalDir`    | `$XDG_DATA_HOME`   or `$HOME/.local/share`                       | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}` | `$HOME/Library/Application Support` |
| `executableDir`   | `$XDG_BIN_HOME` or `$XDG_DATA_HOME/../bin` or `$HOME/.local/bin` | `null`                                   | `null`                              |
| `runtimeDir`      | `$XDG_RUNTIME_DIR` or `null`                                     | `null`                                   | `null`                              |

### `UserDirectories`

The intended use-case for `UserDirectories` is to query the paths of user-facing standard directories
that have been defined according to the conventions of the operating system the library is running on.

| Static field name | Value on Linux / BSD                            | Value on Windows                         | Value on macOS        |
| ----------------- | ----------------------------------------------- | ---------------------------------------- | --------------------- |
| `homeDir`         | `$HOME`                                         | `{5E6C858F-0E22-4760-9AFE-EA3317B67173}` | `$HOME`               |
| `audioDir`        | `$XDG_MUSIC_DIR`                                | `{4BD8D571-6D19-48D3-BE97-422220080E43}` | `$HOME/Music`         |
| `desktopDir`      | `$XDG_DESKTOP_DIR`                              | `{B4BFCC3A-DB2C-424C-B029-7FE99A87C641}` | `$HOME/Desktop`       |
| `documentDir`     | `$XDG_DOCUMENTS_DIR`                            | `{FDD39AD0-238F-46AF-ADB4-6C85480369C7}` | `$HOME/Documents`     |
| `downloadDir`     | `$XDG_DOWNLOAD_DIR`                             | `{374DE290-123F-4565-9164-39C4925E467B}` | `$HOME/Downloads`     |
| `fontDir`         | `$XDG_DATA_HOME/fonts` or `/.local/share/fonts` | `{FD228CB7-AE11-4AE3-864C-16F3910AB8FE}` | `$HOME/Library/Fonts` |
| `pictureDir`      | `$XDG_PICTURES_DIR`                             | `{33E28130-4E1E-4676-835A-98395C3BC3BB}` | `$HOME/Pictures`      |
| `publicDir`       | `$XDG_PUBLICSHARE_DIR`                          | `{DFDF76A2-C82A-4D63-906A-5644AC457385}` | `$HOME/Public`        |
| `templateDir`     | `$XDG_TEMPLATES_DIR`                            | `{A63293E8-664E-48DB-A079-DF759E0509F7}` | `null`                |
| `videoDir`        | `$XDG_VIDEOS_DIR`                               | `{18989B1D-99B5-455B-841C-AB7C74E4DDFC}` | `$HOME/Movies`        |

### `ProjectDirectories`

The intended use-case for `BaseDirectories` is to compute the location of cache, config or data folders for your own application or project,
which are derived from the standard directories.

| Instance field name | Value on Linux / BSD                                                   | Value on Windows                                              | Value on macOS                                     |
| ------------------- | ---------------------------------------------------------------------- | ------------------------------------------------------------- | -------------------------------------------------- |
| `cacheDir`          | `$XDG_CACHE_HOME/<project_path>` or `$HOME/.cache/<project_path>`      | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}/<project_path>/cache` | `$HOME/Library/Caches/<project_path>`              |
| `configDir`         | `$XDG_CONFIG_HOME/<project_path>`  or `$HOME/.config/<project_path>`   | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}/<project_path>`       | `$HOME/Library/Preferences/<project_path>`         |
| `dataDir`           | `$XDG_DATA_HOME/<project_path>` or `$HOME/.local/share/<project_path>` | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}/<project_path>`       | `$HOME/Library/Application Support/<project_path>` |
| `dataLocalDir`      | `$XDG_DATA_HOME/<project_path>` or `$HOME/.local/share/<project_path>` | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}/<project_path>`       | `$HOME/Library/Application Support/<project_path>` |
| `runtimeDir`        | `$XDG_RUNTIME_DIR/<project_path>`                                      | `null`                                                        | `null`                                             |

The specific value of `<project_path>` is computed by the

    ProjectDirectories.from(String qualifier,
                            String organization,
                            String application)

method and varies across operating systems. As an example, calling

    ProjectDirectories.from("org"         /*qualifier*/,
                            "Baz Corp"    /*organization*/,
                            "Foo Bar-App" /*project*/)

results in the following values:


| Value on Linux | Value on Windows         | Value on macOS               |
| -------------- | ------------------------ | ---------------------------- |
| `"foobar-app"` | `"Baz Corp/Foo Bar-App"` | `"org.Baz-Corp.Foo-Bar-App"` |

The `ProjectDirectories.fromPath` method allows the creation of `ProjectDirectories` instances directly from a project path.
This argument is used verbatim and is not adapted to operating system standards.
The use of `ProjectDirectories.fromPath` is strongly discouraged, as its results will not follow operating system standards on at least two of three platforms.

## Versioning

The version number of this library consists of a whole number, which is incremented with each release.
(Think semantic versioning without _minor_ and _patch_ versions.)

## Changelog

### 9 – current stable version 

- Windows:
  - Fixed the implementation of `audioDir`, `desktopDir`, `documentDir`, `pictureDir` and `videoDir`
  - Clarified that `downloadDir` and `publicDir` return `null` on Windows, as the `SpecialFolder` enumeration lacks entries for these values

### 8

- Windows:
  - Fixed incorrect `ProjectDirectories` paths for `cacheDir`, `configDir` and `dataDir`
- Linux:
  - Fixed incorrect `ProjectDirectories` paths for `cacheDir`, `configDir`, `dataDir` and `dataLocalDir` if XDG environment variables are set
  - Fixed incorrect `UserDirectories` paths for `fontDir` if XDG environment variables are set

### 7

- Split `BaseDirectories` into `BaseDirectories` (cache, config, ...) and `UserDirectories` (audio,
  video, ...)
- Fields of `BaseDirectories` and `UserDirectories` are now public
- Add substantial amounts of documentation
- The fields of `UserDirectories` are now `null` if no values can be retrieved from `xdg-user-dirs`,
  instead of falling back on hard-coded values
- A user's home directory is now retrieved with `System.getProperty("user.home")` instead of
  `System.getenv("HOME")`, improving reliability and portability
- Changes to some values of `BaseDirectories` and `ProjectDirectories`:

| Method                            | Old value                                               | New Value                                                    |
| --------------------------------- | ------------------------------------------------------- | ------------------------------------------------------------ |
| `BaseDirectories.cacheDir`        | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}/cache`          | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}`                     |
| `ProjectDirectories#dataDir`      | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}/<project_path>` | `{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}/<project_path>/data` |
| `ProjectDirectories#dataLocalDir` | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}/<project_path>` | `{F1B32785-6FBA-4FCF-9D55-7B8E7F157091}/<project_path>/data` |

### 6

- More consistent and intuitive naming scheme (all names are singular now):

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

- `ProjectDirectories` factory methods have been overhauled and replaced with a single `from(String qualifier, String organization, String project)` method

- Changes to the selection of local/roaming data directories
  - `dataDir` and selects the roaming data directory on Windows (no change on Linux or macOS)
  - `dataLocalDir` and selects the local data directory on Windows (no change on Linux or macOS)

- `ProjectDirectories` received a `runtimeDir` field

- `ProjectDirectories` field names dropped the `project` prefix

- Changes to the directory for executables
  - Support for `executableDir` has been dropped on macOS
  - The value of `executableDir` considers `$XDG_BIN_HOME` now, before falling back to `$XDG_DATA_HOME/../bin` and `$HOME/.local/bin`
