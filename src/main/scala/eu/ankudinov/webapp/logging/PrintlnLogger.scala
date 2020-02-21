package eu.ankudinov.webapp.logging

import cats.Applicative
import eu.ankudinov.webapp.algebras.LoggerAlgebra

import cats.syntax.applicative._
import cats.syntax.apply._

class PrintlnLogger[F[_] : Applicative] extends LoggerAlgebra[F] {
  def trace(msg: => String): F[Unit] = println(s"[trace] $msg").pure[F]
  def info(msg: => String): F[Unit] = println(s"[info] $msg").pure[F]
  def warn(msg: => String): F[Unit] = println(s"[warn] $msg").pure[F]
  def error(msg: => String): F[Unit] = println(s"[error] $msg").pure[F]

  def error(msg : => String, cause : Throwable) : F[Unit] =
    println(s"[error] $msg caused by ${cause.getMessage}").pure[F] *>
      cause.printStackTrace().pure[F]
}
