# AWS Glue SBT Quickstart

This repo provides a quickstart for new AWS Glue projects using Scala. Using SBT and the AWS Glue SDK,
this repo enables local development and unit testing of AWS Glue scripts.

## Monorepo Pattern
AWS Glue restricts users to submitting a single file to execute a job. These files are then compiled by
AWS during the initial stage of job execution (to ensure that they are compiled using the correct Spark
version as the Spark executors it will be run on). This means that any generic or shared logic used by
multiple scripts would have to be repeated in each file, which is against DRY principles. To correct
this we can abstract any shared logic into a separate module, package it ourselves, and provide it as 
an internal dependency to our Glue scripts. To accomplish this we structure the quickstart as a 
monorepo containing two modules:

* shared - this contains abstracted shared logic for interfacing with the underlying Glue & Spark 
contexts, and any other functionality we may miss to shared cross multiple scripts (e.g. abstract base
classes, traits, dataclasses, cleansers, readers, metrics, reporting, etc). This is made available as a
compiled jar and provided to the Glue job via the `--extra-jars` parameter. The purpose of this jar is to 
simplify each script by abstracting complex code patterns into base classes and simple reusable functions.

* scripts - this contains individual Glue scripts, using elements from the shared library. This will
be compiled by AWS Glue prior to execution.

## Getting Started
1. Fork or clone the repo `git clone git@github.com/jhole89/aws-glue-sbt-quickstart.git`
2. In `build.sbt` update the `projectName` and `organization`
3. Run a full sanity check `sbt sanity`

You should now have a running quickstart which you can further develop for your needs. A typical pattern
would be
1. Start writing your Glue scripts & tests
2. Abstract any shared functionality into the shared lib
3. Run a full sanity check `sbt sanity`
4. Upload your Glue scripts (located at `scripts/src/main/scala/scripts/Script.scala`) and shared 
library jar (located at `shared/target/*.jar`) to AWS S3 buckets
5. Create a AWS Glue Job which points to the script, set the main class using the parameter 
`--class scripts.Script`, set the shared dependency using the parameter 
`--extra-jars <your_jar_name>.jar`

## sbt sanity? Thats not a command!
`sbt sanity` is simply an alias used to bind multiple stages of building together. So instead of having
to run `sbt compile`, `sbt test`, `sbt scalafmtAll`, `sbt scalastyle`, and `sbt assembly` separately, 
we can just run `sbt sanity` to call each of these sequentially. It's definition is in the `build.sbt` 
via `addCommandAlias("sanity", ";clean ;compile ;test ;scalafmtAll ;scalastyle ;assembly")`.

## Extending
Maybe this simple structure doesn't suit all your needs. Maybe you need another module within the 
monorepo for something else, or maybe you need multiple shared libs for different business logic? This
is fine and easily done within this framework. If you need other modules, simply declare them in the
`build.sbt` as was done for the shared, and copy over the relevant settings and options. Remember to 
link your dependencies together using the `.dependsOn` syntax.
