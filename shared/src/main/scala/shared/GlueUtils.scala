package shared

import com.amazonaws.services.glue.util.GlueArgParser

object GlueUtils {
// Parses Args from Glue parameters, add additional args to the Sequence.
  def parseArgs(sysArgs: Array[String]): Map[String, String] =
    GlueArgParser
      .getResolvedOptions(sysArgs, Seq("JOB_NAME", "myArg").toArray)
}
