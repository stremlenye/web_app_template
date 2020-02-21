package eu.ankudinov.webapp.algebras

import cats.tagless.FunctorK
import cats.tagless.Derive

trait SomeAlgebra[F[_]] {
  def foo(i: Int): F[String]
}

object SomeAlgebra {
  implicit val functorK: FunctorK[SomeAlgebra] = Derive.functorK
}
