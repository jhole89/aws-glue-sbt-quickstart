package shared

import org.apache.log4j.Logger

object Logging extends Serializable {
  @transient lazy val log: Logger = Logger.getLogger(getClass.getName)
}
