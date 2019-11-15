/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package catslib

import cats.implicits._
import cats.Monad

object MonadHelpers {
  case class OptionT[F[_], A](value: F[Option[A]])

  implicit def optionTMonad[F[_]](implicit F: Monad[F]) = {
    new Monad[OptionT[F, *]] {
      def pure[A](a: A): OptionT[F, A] = OptionT(F.pure(Some(a)))
      def flatMap[A, B](fa: OptionT[F, A])(f: A ⇒ OptionT[F, B]): OptionT[F, B] =
        OptionT {
          F.flatMap(fa.value) {
            case None    ⇒ F.pure(None)
            case Some(a) ⇒ f(a).value
          }
        }

      def tailRecM[A, B](a: A)(f: A => OptionT[F, Either[A, B]]): OptionT[F, B] =
        OptionT(
          F.tailRecM(a)(
            a0 =>
              F.map(f(a0).value)(
                _.fold(Either.right[A, Option[B]](None))(_.map(b => Some(b): Option[B]))
            )))
    }
  }
}
