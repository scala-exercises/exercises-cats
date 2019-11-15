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

class TraverseSpec extends RefSpec with Checkers {
  def `traverseu function with either` = {
    check(
      Test.testSuccess(
        TraverseSection.traverseuFunction _,
        List(1, 2, 3) :: true :: HNil
      )
    )
  }

  def `traverseu function with validated` = {
    check(
      Test.testSuccess(
        TraverseSection.traverseuValidated _,
        true :: HNil
      )
    )
  }

  def `sequencing effects` = {
    val aNone: Option[List[Int]] = None

    check(
      Test.testSuccess(
        TraverseSection.sequencing _,
        Option(List(1, 2, 3)) :: aNone :: HNil
      )
    )
  }

  def `traversing for effects` = {
    val aNone: Option[Unit] = None

    check(
      Test.testSuccess(
        TraverseSection.traversingForEffects _,
        Option(()) :: aNone :: HNil
      )
    )
  }
}
