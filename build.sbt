lazy val root = (project in file("."))
  .settings(
    name                 := "directories",
    organization         := "io.github.soc",
    managedScalaInstance := false,
    crossPaths           := false,
    version              := "6",
    homepage             := Some(url("https://github.com/soc/directories")),
    licenses             := Seq("Mozilla Public License 2.0" -> url("https://opensource.org/licenses/MPL-2.0")),
    libraryDependencies  += "junit" % "junit" % "4.12" % Test,
    pomExtra             :=
      <scm>
        <url>git@github.com:soc/directories.git</url>
        <connection>scm:git:git@github.com:soc/directories.git</connection>
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
