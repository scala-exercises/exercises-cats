package catslib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class IdentitySpec extends Spec with Checkers {
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
