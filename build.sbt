lazy val root = (project in file("."))
  .settings(
    name                 := "directories",
    organization         := "io.github.soc",
    managedScalaInstance := false,
    crossPaths           := false,
    version              := "11",
    homepage             := Some(url("https://github.com/soc/directories-jvm")),
    licenses             := Seq("Mozilla Public License 2.0" -> url("https://opensource.org/licenses/MPL-2.0")),
    fork                 := true,
    // The javaHome setting can be removed if building against the latest installed version of Java is acceptable.
    // Running the tests requires removing the setting.
    // It can also be changed to point to a different Java version.
    // javaHome             := Some(file("/home/soc/apps/zulu6.19.0.1-jdk6.0.103-linux_x64/")),
    libraryDependencies  += "junit"        % "junit"           % "4.13" % Test,
    libraryDependencies  += "com.novocode" % "junit-interface" % "0.11" % Test,
    testOptions in Test  := Seq(Tests.Argument(TestFrameworks.JUnit, "-a")),
    // publishTo            := sonatypePublishTo.value,
    packageOptions in (Compile, packageBin) += {
      import java.util.jar.{Attributes, Manifest}
      val manifest = new Manifest
      manifest.getMainAttributes.put(new Attributes.Name("Automatic-Module-Name"), "io.github.soc.directories")
      Package.JarManifest(manifest)
    },
    pomExtra             :=
      <scm>
        <url>git@github.com:soc/directories-jvm.git</url>
        <connection>scm:git:git@github.com:soc/directories-jvm.git</connection>
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
