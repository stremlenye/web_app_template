package eu.ankudinov.webapp

import com.twitter.finagle.Http
import com.twitter.util.Await
import eu.ankudinov.webapp.apis.SomeApi
import eu.ankudinov.webapp.config.Config
import io.finch.circe._
import pureconfig.ConfigSource

object Server extends App {

  ConfigSource.default.load[Config].fold(
    failure => sys.error(s"Failed loading configuration. Cause: $failure"),
    (_: Config) => {
      val api = new SomeApi
      Await.ready(Http.server.serve(":8081", api.api.toService))
      ()
    }
  )
}
