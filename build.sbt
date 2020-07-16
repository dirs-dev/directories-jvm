lazy val sharedSettings = Def.settings(
  organization         := "dev.dirs",
  managedScalaInstance := false,
  crossPaths           := false,
  version              := "20",
  homepage             := Some(url("https://github.com/dirs-dev/directories-jvm")),
  licenses             := Seq("Mozilla Public License 2.0" -> url("https://opensource.org/licenses/MPL-2.0")),
  fork                 := true,
  // The javaHome setting can be removed if building against the latest installed version of Java is acceptable.
  // Running the tests requires removing the setting.
  // It can also be changed to point to a different Java version.
  // javaHome             := Some(file("/home/soc/apps/zulu6.19.0.1-jdk6.0.103-linux_x64/")),
  /*
  publishTo            := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  */
  pomIncludeRepository := { _ => false },
  pomExtra             :=
    <scm>
      <url>git@github.com:dirs-dev/directories-jvm.git</url>
      <connection>scm:git:git@github.com:dirs-dev/directories-jvm.git</connection>
    </scm>
    <developers>
      <developer>
        <id>soc</id>
        <name>Simon Ochsenreither</name>
        <url>https://github.com/soc</url>
        <roles>
          <role>Project Lead</role>
        </roles>
      </developer>
    </developers>
)

def automaticModuleName(name: String) = Def.settings(
  packageOptions in (Compile, packageBin) += {
    import java.util.jar.{Attributes, Manifest}
    val manifest = new Manifest
    manifest.getMainAttributes.put(new Attributes.Name("Automatic-Module-Name"), name)
    Package.JarManifest(manifest)
  }
)

lazy val core = project
  .settings(
    sharedSettings,
    name                 := "directories",
    libraryDependencies  += "junit"        % "junit"           % "4.13" % Test,
    libraryDependencies  += "com.novocode" % "junit-interface" % "0.11" % Test,
    testOptions in Test  := Seq(Tests.Argument(TestFrameworks.JUnit, "-a")),
    automaticModuleName("dev.dirs")
  )

lazy val cli = project
  .dependsOn(core)
  .settings(
    sharedSettings,
    name                 := "directories-cli",
    libraryDependencies  += "info.picocli" % "picocli" % "4.4.0",
    automaticModuleName("dev.dirs.cli")
  )
