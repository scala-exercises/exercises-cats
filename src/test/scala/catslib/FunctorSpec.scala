/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package catslib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.refspec.RefSpec
import shapeless.HNil

class FunctorSpec extends RefSpec with Checkers {
  def `using functor` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        FunctorSection.usingFunctor _,
        Option(5) :: theNone :: HNil
      )
    )
  }

  def `lifting to a functor` = {
    check(
      Test.testSuccess(
        FunctorSection.liftingToAFunctor _,
        Option(5) :: HNil
      )
    )
  }

  def `using fproduct` = {
    check(
      Test.testSuccess(
        FunctorSection.usingFproduct _,
        4 :: 2 :: 7 :: HNil
      )
    )
  }

  def `composing functors` = {
    val result: List[Option[Int]] = List(Option(2), None, Option(4))

    check(
      Test.testSuccess(
        FunctorSection.composingFunctors _,
        result :: HNil
      )
    )
  }
}
