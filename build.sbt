lazy val cats = (project in file("."))
.enablePlugins(ExerciseCompilerPlugin)
.settings(
  organization := "org.scalaexercises",
  name            := "content-cats",
  scalaVersion := "2.11.7",
  version := "0.0.0-SNAPSHOT",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "0.4.1",
    "com.chuusai" %% "shapeless" % "2.2.5",
    "org.scalatest" %% "scalatest" % "2.2.4",
    "org.scalaexercises" %% "runtime" % "0.0.0-SNAPSHOT" changing(),
    "org.scalaexercises" %% "definitions" % "0.0.0-SNAPSHOT" changing(),
    "org.scalacheck" %% "scalacheck" % "1.12.5",
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.12" % "0.3.1",
    compilerPlugin("org.spire-math" %% "kind-projector" % "0.7.1")
  )
)
