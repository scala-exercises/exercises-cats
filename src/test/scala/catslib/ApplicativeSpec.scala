package catslib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class ApplicativeSpec extends Spec with Checkers {
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
