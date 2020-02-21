package eu.ankudinov.webapp.persistence

import cats.effect.IO
import doobie.scalatest.IOChecker
import doobie.util.transactor.Transactor
import eu.ankudinov.webapp.common.executors
import eu.ankudinov.webapp.config.Config
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import pureconfig.ConfigSource

class SomeRepositoryTest extends AnyFunSuite with Matchers with IOChecker {
  implicit val contextShift = IO.contextShift(executors.blockingIoEc)

  val config: Config = ConfigSource.default.load[Config].fold(
    f => {
      println(f)
      throw new RuntimeException()
    },
    identity
  )

  val transactor = Transactor.fromDriverManager[IO](
    config.postgres.driverClassName,
    config.postgres.url,
    config.postgres.user,
    config.postgres.password
  )


  test("insert")(check(SomeRepository.queries.insert(1, "1")))
  test("fetch")(check(SomeRepository.queries.fetch(1)))
}
