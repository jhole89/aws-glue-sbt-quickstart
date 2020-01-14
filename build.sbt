name in ThisBuild := "etl"
organization in ThisBuild := "com.myOrg"
scalaVersion in ThisBuild := "2.11.12"
version in ThisBuild := "1.0"

addCommandAlias(
  "sanity",
  ";clean ;compile ;test ;scalafmtAll ;scalastyle; assembly"
)

lazy val schemas = project
  .settings(settings, libraryDependencies ++= commonDependencies)

lazy val framework = project
  .settings(settings, libraryDependencies ++= commonDependencies)

lazy val scripts = project
  .settings(settings, libraryDependencies ++= commonDependencies)
  .dependsOn(framework % "compile->compile;test->test", schemas % "compile->compile;test->test")

lazy val settings = Seq(
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
