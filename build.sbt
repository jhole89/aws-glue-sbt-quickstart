val projectName = "myproj"
val organization = "myorg"
val version = "1.0"
scalaVersion in ThisBuild := "2.11.12"

addCommandAlias(
  "sanity",
  ";clean ;compile ;test ;scalafmtAll ;scalastyle ;assembly"
)

lazy val root = (project in file("."))
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .aggregate(shared, scripts)
  .settings(settings, libraryDependencies ++= commonDependencies)

lazy val shared = project
  .disablePlugins(ScalafmtPlugin)
  .disablePlugins(ScalastylePlugin)
  .settings(settings, libraryDependencies ++= commonDependencies)

lazy val scripts = project
  .disablePlugins(ScalafmtPlugin)
  .disablePlugins(ScalastylePlugin)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(settings, libraryDependencies ++= commonDependencies)
  .dependsOn(shared % "compile->compile;test->test")

lazy val settings = Seq(
  assemblyJarName in assembly := s"$organization-$projectName-${name.value}-assembly-$version.jar",
  test in assembly := {},
  scalacOptions ++= Seq(),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    "aws-glue-etl-artifacts" at "https://aws-glue-etl-artifacts.s3.amazonaws.com/release/"
  )
)

lazy val commonDependencies = Seq(
  "com.amazonaws" % "AWSGlueETL" % "1.0.0" % Provided,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

fork in ThisBuild := true
parallelExecution in ThisBuild := false
logBuffered in ThisBuild := false
testOptions in ThisBuild += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

javaOptions ++= Seq(
  "-XX:+CMSClassUnloadingEnabled",
  "-XX:MaxMetaspaceSize=512M",
  "-XX:MetaspaceSize=256M",
  "-Xms512M",
  "-Xmx2G",
  "-XX:MaxPermSize=2048M"
)
