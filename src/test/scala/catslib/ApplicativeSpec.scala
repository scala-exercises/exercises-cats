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

class ApplicativeSpec extends RefSpec with Checkers {
  def `pure method` = {
    check(
      Test.testSuccess(
        ApplicativeSection.pureMethod _,
        Option(1) :: List(1) :: HNil
      )
    )
  }

  def `applicative composition` = {
    check(
      Test.testSuccess(
        ApplicativeSection.applicativeComposition _,
        List(Option(1)) :: HNil
      )
    )
  }

  def `applicative and monad` = {
    check(
      Test.testSuccess(
        ApplicativeSection.applicativesAndMonads _,
        Option(1) :: Option(1) :: HNil
      )
    )
  }
}
