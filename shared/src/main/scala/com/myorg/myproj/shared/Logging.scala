package com.myorg.myproj.shared

import java.util.logging.Logger

object Logging extends Serializable {
  @transient lazy val log: Logger = Logger.getLogger(getClass.getName)
}
