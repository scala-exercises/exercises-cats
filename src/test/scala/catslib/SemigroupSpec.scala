/*
 * scala-exercises - exercises-cats
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package catslib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.prop.Checkers
import org.scalatest.refspec.RefSpec
import shapeless.HNil

class SemigroupSpec extends RefSpec with Checkers {
  def `has a combine operation` = {
    check(
      Test.testSuccess(
        SemigroupSection.semigroupCombine _,
        3 :: List(1, 2, 3, 4, 5, 6) :: Option(3) :: Option(1) :: 67 :: HNil
      )
    )
  }

  def `composes with other semigroups` = {
    check(
      Test.testSuccess(
        SemigroupSection.composingSemigroups _,
        Map("bar" → 11) :: HNil
      )
    )
  }

  def `has special syntax` = {
    val aNone: Option[Int] = None

    check(
      Test.testSuccess(
        SemigroupSection.semigroupSpecialSyntax _,
        Option(3) :: Option(2) :: aNone :: Option(2) :: HNil
      )
    )
  }
}
