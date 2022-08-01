[![Maven Central](https://img.shields.io/maven-central/v/dev.dirs/directories.svg?style=for-the-badge)](https://search.maven.org/#search|gav|1|g%3A%22dev.dirs%22%20AND%20a%3A%22directories%22)
[![API documentation](http://javadoc.io/badge/dev.dirs/directories.svg?style=for-the-badge)](http://javadoc.io/doc/dev.dirs/directories)
![actively developed](https://img.shields.io/badge/maintenance-actively_developed-brightgreen.svg?style=for-the-badge)
[![GitHub Actions status](https://img.shields.io/github/workflow/status/dirs-dev/directories-jvm/Linux%20build?style=for-the-badge)](https://github.com/dirs-dev/directories-jvm/actions/workflows/test.yml)
[![AppVeyor status](https://img.shields.io/appveyor/ci/soc/directories-jvm/main.svg?label=Windows%20build&style=for-the-badge)](https://ci.appveyor.com/project/soc/directories-jvm/branch/main)
[![License: MPL-2.0](https://img.shields.io/github/license/dirs-dev/directories-jvm.svg?style=for-the-badge)](LICENSE)

# Directories

## Introduction

- a tiny library (12kB) with a minimal API
- that provides the platform-specific, user-accessible locations
- for retrieving and storing configuration, cache and other data
- on Linux, Windows (≥ 7), macOS and BSD

The library provides the location of these directories by leveraging the mechanisms defined by
- the [XDG base directory](https://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html) and
  the [XDG user directory](https://www.freedesktop.org/wiki/Software/xdg-user-dirs/) specifications on Linux
- the [Known Folder](https://msdn.microsoft.com/en-us/library/windows/desktop/dd378457.aspx) API on Windows
- the [Standard Directories](https://developer.apple.com/library/content/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/FileSystemOverview/FileSystemOverview.html#//apple_ref/doc/uid/TP40010672-CH2-SW6)
  guidelines on macOS

## Platforms

This library is written in Java, and runs on the JVM (≥ 6).

A version of this library implemented in Rust is provided by [directories-rs](https://github.com/dirs-dev/directories-rs).

## Usage

#### Dependency

Add the library as a dependency to your project:

##### Maven
```xml
<dependency>
  <groupId>dev.dirs</groupId>
  <artifactId>directories</artifactId>
  <version>26</version>
</dependency>
```
##### Gradle
```groovy
compile 'dev.dirs:directories:26'
```

##### SBT
```scala
"dev.dirs" % "directories" % "26"
```

The library itself is built against Java 6 to allow for the widest possible usage scenarios.
It can be used on any Java version >= 6.

#### Example

Library run by user Alice:

```java
import dev.dirs.ProjectDirectories;
import dev.dirs.BaseDirectories;
import dev.dirs.UserDirectories;

ProjectDirectories myProjDirs = ProjectDirectories.from("com", "Foo Corp", "Bar App");
myProjDirs.configDir;
// Lin: /home/alice/.config/barapp
// Mac: /Users/Alice/Library/Application Support/com.Foo-Corp.Bar-App
// Win: C:\Users\Alice\AppData\Roaming\Foo Corp\Bar App\config

BaseDirectories baseDirs = BaseDirectories.get();
baseDirs.executableDir;
// Lin: /home/alice/.local/bin
// Mac: null
// Win: null

UserDirectories userDirs = UserDirectories.get();
userDirs.audioDir;
// Lin: /home/alice/Music
// Mac: /Users/Alice/Music
// Win: C:\Users\Alice\Music
```

## Design Goals

- The _directories_ library is designed to provide an accurate snapshot of the system's state at
  the point of invocation of `BaseDirectories.get()`, `UserDirectories.get()` or
  `ProjectDirectories.from()`. Subsequent changes to the state of the system are not reflected in
  instances created prior to such a change.
- This library does not create directories or check for their existence. The library only provides
  information on what the path to a certain directory _should_ be. How this information is used is
  a decision that developers need to make based on the requirements of each individual application.
- This library is intentionally focused on providing information on user-writable directories only.
  There is no discernible benefit in returning a path that points to a user-level, writable
  directory on one operating system, but a system-level, read-only directory on another, that would
  outweigh the confusion and unexpected failures such an approach would cause.
  - `executableDir` is specified to provide the path to a user-writable directory for binaries.<br/>
    As such a directory only commonly exists on Linux, it returns `null` on macOS and Windows.
  - `fontDir` is specified to provide the path to a user-writable directory for fonts.<br/>
    As such a directory only exists on Linux and macOS, it returns `null` Windows.
  - `runtimeDir` is specified to provide the path to a directory for non-essential runtime data.
    It is required that this directory is created when the user logs in, is only accessible by the
    user itself, is deleted when the user logs out, and supports all filesystem features of the
    operating system.<br/>
    As such a directory only commonly exists on Linux, it returns `null` on macOS and Windows.

## Features

### `BaseDirectories`

The intended use-case for `BaseDirectories` is to query the paths of user-invisible standard directories
that have been defined according to the conventions of the operating system the library is running on.

If you want to compute the location of cache, config or data folders for your own application or project, use `ProjectDirectories` instead.

| Field name     | Value on Linux / BSD / Solaris                                   | Value on Windows                  | Value on macOS                      |
| -------------- | ---------------------------------------------------------------- | --------------------------------- | ----------------------------------- |
| `homeDir`      | `$HOME`                                                          | `{FOLDERID_UserProfile}`          | `$HOME`                             |
| `cacheDir`     | `$XDG_CACHE_HOME`  or `$HOME`/.cache                             | `{FOLDERID_LocalApplicationData}` | `$HOME`/Library/Caches              |
| `configDir`    | `$XDG_CONFIG_HOME` or `$HOME`/.config                            | `{FOLDERID_ApplicationData}`      | `$HOME`/Library/Application Support |
| `dataDir`      | `$XDG_DATA_HOME`   or `$HOME`/.local/share                       | `{FOLDERID_ApplicationData}`      | `$HOME`/Library/Application Support |
| `dataLocalDir` | `$XDG_DATA_HOME`   or `$HOME`/.local/share                       | `{FOLDERID_LocalApplicationData}` | `$HOME`/Library/Application Support |
| `executableDir`| `$XDG_BIN_HOME` or `$XDG_DATA_HOME`/../bin or `$HOME`/.local/bin | `null`                            | `null`                              |
| `preferenceDir`| `$XDG_CONFIG_HOME` or `$HOME`/.config                            | `{FOLDERID_ApplicationData}`      | `$HOME`/Library/Preferences         |
| `runtimeDir`   | `$XDG_RUNTIME_DIR` or `null`                                     | `null`                            | `null`                              |

### `UserDirectories`

The intended use-case for `UserDirectories` is to query the paths of user-facing standard directories
that have been defined according to the conventions of the operating system the library is running on.

| Field name    | Value on Linux / BSD                                 | Value on Windows         | Value on macOS        |
| ------------- | ---------------------------------------------------- | ------------------------ | --------------------- |
| `homeDir`     | `$HOME`                                              | `{FOLDERID_UserProfile}` | `$HOME`               |
| `audioDir`    | `XDG_MUSIC_DIR`                                      | `{FOLDERID_Music}`       | `$HOME`/Music         |
| `desktopDir`  | `XDG_DESKTOP_DIR`                                    | `{FOLDERID_Desktop}`     | `$HOME`/Desktop       |
| `documentDir` | `XDG_DOCUMENTS_DIR`                                  | `{FOLDERID_Documents}`   | `$HOME`/Documents     |
| `downloadDir` | `XDG_DOWNLOAD_DIR`                                   | `{FOLDERID_Downloads}`   | `$HOME`/Downloads     |
| `fontDir`     | `$XDG_DATA_HOME`/fonts or `$HOME`/.local/share/fonts | `null`                   | `$HOME`/Library/Fonts |
| `pictureDir`  | `XDG_PICTURES_DIR`                                   | `{FOLDERID_Pictures}`    | `$HOME`/Pictures      |
| `publicDir`   | `XDG_PUBLICSHARE_DIR`                                | `{FOLDERID_Public}`      | `$HOME`/Public        |
| `templateDir` | `XDG_TEMPLATES_DIR`                                  | `{FOLDERID_Templates}`   | `null`                |
| `videoDir`    | `XDG_VIDEOS_DIR`                                     | `{FOLDERID_Videos}`      | `$HOME`/Movies        |

### `ProjectDirectories`

The intended use-case for `ProjectDirectories` is to compute the location of cache, config or data folders for your own application or project,
which are derived from the standard directories.

| Field name      | Value on Linux / BSD                                                       | Value on Windows                                         | Value on macOS                                       |
| --------------- | -------------------------------------------------------------------------- | -------------------------------------------------------- | ---------------------------------------------------- |
| `cacheDir`      | `$XDG_CACHE_HOME`/`<project_path>` or `$HOME`/.cache/`<project_path>`      | `{FOLDERID_LocalApplicationData}`/`<project_path>`/cache | `$HOME`/Library/Caches/`<project_path>`              |
| `configDir`     | `$XDG_CONFIG_HOME`/`<project_path>`  or `$HOME`/.config/`<project_path>`   | `{FOLDERID_ApplicationData}`/`<project_path>`/config     | `$HOME`/Library/Application Support/`<project_path>` |
| `dataDir`       | `$XDG_DATA_HOME`/`<project_path>` or `$HOME`/.local/share/`<project_path>` | `{FOLDERID_ApplicationData}`/`<project_path>`/data       | `$HOME`/Library/Application Support/`<project_path>` |
| `dataLocalDir`  | `$XDG_DATA_HOME`/`<project_path>` or `$HOME`/.local/share/`<project_path>` | `{FOLDERID_LocalApplicationData}`/`<project_path>`/data  | `$HOME`/Library/Application Support/`<project_path>` |
| `preferenceDir` | `$XDG_CONFIG_HOME`/`<project_path>`  or `$HOME`/.config/`<project_path>`   | `{FOLDERID_ApplicationData}`/`<project_path>`/config     | `$HOME`/Library/Preferences/`<project_path>`         |
| `runtimeDir`    | `$XDG_RUNTIME_DIR`/`<project_path>`                                        | `null`                                                   | `null`                                               |

The specific value of `<project_path>` is computed by the

    ProjectDirectories.from(String qualifier,
                            String organization,
                            String application)

method and varies across operating systems. As an example, calling

    ProjectDirectories.from("org"         /*qualifier*/,
                            "Baz Corp"    /*organization*/,
                            "Foo Bar-App" /*application*/)

results in the following values:


| Value on Linux | Value on Windows         | Value on macOS               |
| -------------- | ------------------------ | ---------------------------- |
| `"foobar-app"` | `"Baz Corp/Foo Bar-App"` | `"org.Baz-Corp.Foo-Bar-App"` |

The `ProjectDirectories.fromPath` method allows the creation of `ProjectDirectories` instances directly from a project path.
This argument is used verbatim and is not adapted to operating system standards.
The use of `ProjectDirectories.fromPath` is strongly discouraged, as its results will not follow operating system standards on at least two of three platforms.

## Versioning

The version number of this library consists of a whole number, which is incremented with each release.

## Changelog

### 26 – current stable `dev.dirs:directories` release

- Adds support for IBM Series i (OS/400). Thanks @ThePrez!

### 25
- Release with newer JavaDoc version.
- Update SBT version to 1.5.0.

### 24

- Try both "normal" and "downgraded" version of Powershell.
  Hopefully fixes #47. Thanks @phongngtuan!

### 23

- Try downgrading the Powershell version to avoid Powershell's Constrained Language Mode.
  Hopefully fixes #43. Thanks @fthomas!

### 22

- Restore previous (pre-21) behavior of returning `null` if a directory path can't be retrieved on Windows,
  instead of throwing an exception. Workaround for #43. Thanks @fthomas!

### 21

- Handle missing powershell.exe on the windows PATH. Fixes #21. Thanks @eatkins!
  - Fallback to pwsh.exe if powershell.exe is unavailable
  - Add default locations of each of those executables to the forked process PATH in case neither
    executable is available on the jvm process PATH.
- Pass `-NoProfile` to powershell.exe Fixes #36. Thanks @alexarchambault!
- Ignore UTF-8 BOM on Windows Fixes #37. Thanks @alexarchambault!

### 20

- **BREAKING CHANGE** The behavior of `configDir` on macOS has been adjusted:
  - `BaseDirectories#configDir` and `ProjectDirectories#configDir` have been changed to use the `Application Support` directory,
    [as suggested by Apple documentation](https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/MacOSXDirectories/MacOSXDirectories.html).
  - If you have used `BaseDirectories#configDir` or `ProjectDirectories#configDir` to store files,
    it may be necessary to write code that migrates the files to the new location.
    (Alternative: change usages of `configDir` to `preferenceDir` to keep the old (pre-v20) behavior.)
  - The behavior of `configDir` on non-macOS platforms has not been changed.
- `BaseDirectories#preferenceDir` and `ProjectDirectories#preferenceDir` have been added:
  - They use the `Preferences` directory on macOS, like `configDir` did before version 20.
  – `preferenceDir` behaves identical to `configDir` on non-macOS platforms.

### 19-13 – Reserved for bug fixes on `soc.github.io`

### 12 – current stable legacy `soc.github.io` release

_Please refer to [the legacy branch](https://github.com/dirs-dev/directories-jvm/tree/legacy) for the documentation and artifact coordinates of the `soc.github.io` artifact._

- Adjust library to deal with breaking changes in Java caused by CVE-2019-2958 (see JDK-8221858). Thanks @alexarchambault!
- Support Solaris. Thanks @tomasjura!
- Add reflect-config.json file for GraalVM native-image. Thanks @alexarchambault!

### 11

- Throw RuntimeExceptions instead of catching IOExceptions and returning null if directory lookup on Windows fails.
  This should make it easier to diagnose issues.
- Add Automatic-Module-Name to manifest file, enabling support for Java 9 modules.
- Some small cleanups.

### 10

- Full Windows support: `downloadDir` and `publicDir` are now supported.
- Improved speed on Windows and Linux.
- Changed static fields in `BaseDirectories` and `UserDirectories` to instance fields.
  Instances can be created with `BaseDirectories.get()` and `UserDirectories.get()`.

### 9

- Windows:
  - Fixed the implementation of `audioDir`, `desktopDir`, `documentDir`, `pictureDir` and `videoDir`.
  - Clarified that `downloadDir` and `publicDir` return `null` on Windows, as the `SpecialFolder` enumeration lacks entries for these values.

### 8

- Windows:
  - Fixed incorrect `ProjectDirectories` paths for `cacheDir`, `configDir` and `dataDir`.
- Linux:
  - Fixed incorrect `ProjectDirectories` paths for `cacheDir`, `configDir`, `dataDir` and `dataLocalDir` if XDG environment variables are set.
  - Fixed incorrect `UserDirectories` paths for `fontDir` if XDG environment variables are set.

### 7

- Split `BaseDirectories` into `BaseDirectories` (cache, config, ...) and `UserDirectories` (audio,
  video, ...).
- Fields of `BaseDirectories` and `UserDirectories` are now public.
- Add substantial amounts of documentation.
- The fields of `UserDirectories` are now `null` if no values can be retrieved from `xdg-user-dirs`,
  instead of falling back on hard-coded values.
- A user's home directory is now retrieved with `System.getProperty("user.home")` instead of
  `System.getenv("HOME")`, improving reliability and portability.
- Changes to some values of `BaseDirectories` and `ProjectDirectories`:

| Method                            | Old value                                               | New Value                                                    |
| --------------------------------- | ------------------------------------------------------- | ------------------------------------------------------------ |
| `BaseDirectories.cacheDir`        | `{SpecialFolder.LocalApplicationData}`/cache            | `{SpecialFolder.LocalApplicationData}`                       |
| `ProjectDirectories#dataDir`      | `{SpecialFolder.ApplicationData}`/`<project_path>`      | `{SpecialFolder.ApplicationData}`/`<project_path>`/data      |
| `ProjectDirectories#dataLocalDir` | `{SpecialFolder.LocalApplicationData}`/`<project_path>` | `{SpecialFolder.LocalApplicationData}`/`<project_path>`/data |

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

- `ProjectDirectories` factory methods have been overhauled and replaced with a single `from(String qualifier, String organization, String project)` method.

- Changes to the selection of local/roaming data directories:
  - `dataDir` selects the roaming data directory on Windows (no change on Linux or macOS).
  - `dataLocalDir` selects the local data directory on Windows (no change on Linux or macOS).

- `ProjectDirectories` received a `runtimeDir` field.

- `ProjectDirectories` field names dropped the `project` prefix.

- Changes to the directory for executables:
  - Support for `executableDir` has been dropped on macOS.
  - The value of `executableDir` considers `$XDG_BIN_HOME` now, before falling back to `$XDG_DATA_HOME/../bin` and `$HOME/.local/bin`.

### 5-1 – Unpublished beta releases
