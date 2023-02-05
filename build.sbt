ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "zio-garage"
  )
// https://mvnrepository.com/artifact/dev.zio/zio
libraryDependencies += "dev.zio" %% "zio" % "2.0.4"
