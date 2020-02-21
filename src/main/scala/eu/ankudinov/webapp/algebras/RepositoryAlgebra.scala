package eu.ankudinov.webapp.algebras

import cats.tagless.{Derive, FunctorK}
import cats.~>

trait RepositoryAlgebra[F[_], I, A] { self =>
  def get(i: I): F[Option[A]]
  def put(i: I, a: A): F[A]

  final def mapK[G[_]](f: F ~> G): RepositoryAlgebra[G, I, A] = RepositoryAlgebra.functorK[I, A].mapK(self)(f)
}

object RepositoryAlgebra {
  implicit def functorK[I, A]: FunctorK[λ[`f[_]` => RepositoryAlgebra[f, I, A]]] = Derive.functorK[λ[`f[_]` => RepositoryAlgebra[f, I, A]]]
}
