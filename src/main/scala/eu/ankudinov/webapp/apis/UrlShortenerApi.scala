package eu.ankudinov.webapp.apis

import io.finch._
import io.finch.syntax._

class SomeApi {
  val root = "api"

  val endpoint = get(root)(Ok(()))

  val api = endpoint
}
