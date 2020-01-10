import sbtassembly.{MergeStrategy, PathList, ShadeRule}

object Assembly {
  def strategy(pathList: String): MergeStrategy = pathList match {
    case PathList("org", "aopalliance", xs@_*) => MergeStrategy.last
    case PathList("javax", "inject", xs@_*) => MergeStrategy.last
    case PathList("org", "apache", xs@_*) => MergeStrategy.last
    case PathList("ch", "qos", "logback", xs@_*) => MergeStrategy.first
    case PathList("org", "slf4j", "slf4j-log4j12", xs@_*) => MergeStrategy.last
    case PathList("com", "codhale", "metrics", xs@_*) => MergeStrategy.discard
    case PathList("io", "dropwizard", "metrics", xs@_*) => MergeStrategy.discard
    case PathList("org", "apache", "calcite", xs@_*) => MergeStrategy.discard
    case PathList("org", "glassfish", "jersey", "core", xs@_*) => MergeStrategy.discard
    case PathList("com", "sun", "jersey", xs@_*) => MergeStrategy.discard
    case PathList("javax", "ws", "rs", xs@_*) => MergeStrategy.discard
    case PathList("stax", xs@_*) => MergeStrategy.discard
    case PathList("xml-apis", xs@_*) => MergeStrategy.discard
    case PathList("javax", "xml", "stream", xs@_*) => MergeStrategy.discard
    case PathList("org", "ow2", "asm", xs@_*) => MergeStrategy.discard
    case PathList("org", "objenesis", xs@_*) => MergeStrategy.discard
    case _ => MergeStrategy.first
  }

  object ShadeRules {
    val shapelessShading: ShadeRule = ShadeRule.rename("shapeless.**" -> "shadeshapless.@1").inAll
  }
}
