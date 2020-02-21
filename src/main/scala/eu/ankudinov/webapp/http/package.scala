package eu.ankudinov.webapp

import cats.data.Kleisli
import com.twitter.finagle.http.Request
import com.twitter.util.Future
import io.finch._
import io.finch.syntax.{Mapper, ToTwitterFuture}

/** Http layer related helpers  */
package object http {
  type HttpHandler[A] = Kleisli[Future, Request, Output[A]]

  implicit def mapperFromFutureOutputKleisli[F[_], A, B](kf: A => Kleisli[F, Request, Output[B]])(
    implicit toTwitter: ToTwitterFuture[F]
  ): Mapper.Aux[A, B] =
    Mapper.instance(_.product(root).mapOutputAsync {
      case (value, req) => toTwitter(kf(value).run(req))
    })
}
