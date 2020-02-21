package eu.ankudinov.webapp.services

import cats.Applicative
import eu.ankudinov.webapp.algebras.SomeAlgebra

class SomeService[F[_]](implicit F: Applicative[F]) extends SomeAlgebra[F] {
  override def foo(i: Int): F[String] = F.pure(i.toString)
}

