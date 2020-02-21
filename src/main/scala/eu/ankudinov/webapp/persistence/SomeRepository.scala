package eu.ankudinov.webapp.persistence

import eu.ankudinov.webapp.algebras.RepositoryAlgebra
import eu.ankudinov.webapp.common.{LiftError, PersistenceException}
import eu.ankudinov.webapp.util.transformations

import cats.data.EitherT
import cats.effect.Bracket
import cats.instances.list._
import cats.syntax.foldable._
import doobie._
import doobie.implicits._

class SomeRepository extends RepositoryAlgebra[ConnectionIO, Int, String] {
  import SomeRepository.queries

  def put(id: Int, value: String): ConnectionIO[String] =
    queries.insert(id, value).run.map(_ => value)

  def get(id: Int): doobie.ConnectionIO[Option[String]] = queries.fetch(id).option
}

object SomeRepository {

  object queries {
    val table = fr"some_table"
    val fields = Seq("id", "value")
    val columns = fields.toList.map(Fragment.const(_)).intercalate(fr",")
    val keys = fields.toList.map(Fragment.const(_)).foldSmash(fr"(", fr",", fr")")

    def fetch(id: Int): Query0[String] =
      (fr"select value from" ++ table ++ fr"where id = $id").query[String]

    def insert(id: Int, value: String): Update0 =
      (fr"insert into " ++ table ++ fr"values ($id, $value)").update
  }

  def transactional[F[_], E <: Throwable](
    implicit F: Bracket[F, Throwable],
    transactor: Transactor[F],
    liftError: LiftError[PersistenceException, E]
  ): RepositoryAlgebra[EitherT[F, E, *], Int, String] =
    (new SomeRepository).mapK[EitherT[F, E, *]](transformations.transactionalFallible[F, E])

}
