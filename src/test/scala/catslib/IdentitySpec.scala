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

class IdentitySpec extends RefSpec with Checkers {
  def `Id type` = {
    check(
      Test.testSuccess(
        IdentitySection.identityType _,
        42 :: HNil
      )
    )
  }

  def `Id has pure` = {
    check(
      Test.testSuccess(
        IdentitySection.pureIdentity _,
        42 :: HNil
      )
    )
  }

  def `Id Comonad` = {
    check(
      Test.testSuccess(
        IdentitySection.idComonad _,
        43 :: HNil
      )
    )
  }
}
