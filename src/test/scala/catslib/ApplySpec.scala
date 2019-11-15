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

class ApplySpec extends RefSpec with Checkers {
  def `extends functor` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyExtendsFunctor _,
        Option("1") :: Option(2) :: theNone :: HNil
      )
    )
  }

  def `is composable` = {
    val result: List[Option[Int]] = List(Some(2), None, Some(4))

    check(
      Test.testSuccess(
        ApplySection.applyComposes _,
        result :: HNil
      )
    )
  }

  def `ap method` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyAp _,
        Option("1") :: Option(2) :: theNone :: theNone :: theNone :: HNil
      )
    )
  }

  def `apN method` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyApn _,
        Option(3) :: theNone :: Option(6) :: HNil
      )
    )
  }

  def `mapN method` = {
    check(
      Test.testSuccess(
        ApplySection.applyMapn _,
        Option(3) :: Option(6) :: HNil
      )
    )
  }

  def `tupleN method` = {
    check(
      Test.testSuccess(
        ApplySection.applyTuplen _,
        Option((1, 2)) :: Option((1, 2, 3)) :: HNil
      )
    )
  }

  def `builder syntax` = {
    val aNone: Option[Int]                   = None
    val anotherNone: Option[(Int, Int, Int)] = None

    check(
      Test.testSuccess(
        ApplySection.applyBuilderSyntax _,
        Option(3) :: aNone :: Option(3) :: aNone :: Option((1, 2)) :: anotherNone :: HNil
      )
    )
  }
}
