package catslib

import cats.Monoid
import cats.std.all._

object MonoidHelpers {
  implicit def monoidTuple[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      def combine(x: (A, B), y: (A, B)): (A, B) = {
        val (xa, xb) = x
        val (ya, yb) = y
        (Monoid[A].combine(xa, ya), Monoid[B].combine(xb, yb))
      }
      def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty)
    }
}
