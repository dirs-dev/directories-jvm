lazy val root = (project in file("."))
  .settings(
    name                 := "directories",
    organization         := "io.github.soc",
    autoScalaLibrary     := false,
    managedScalaInstance := false,
    crossPaths           := false,
    version              := "1"
  )
