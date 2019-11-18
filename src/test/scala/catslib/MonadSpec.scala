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

class MonadSpec extends RefSpec with Checkers {
  def `flatten function` = {
    val aNone: Option[Int] = None

    check(
      Test.testSuccess(
        MonadSection.flattenRecap _,
        Option(1) :: aNone :: List(1, 2, 3) :: HNil
      )
    )
  }

  def `monad instances` = {
    check(
      Test.testSuccess(
        MonadSection.monadInstances _,
        Option(42) :: HNil
      )
    )
  }

  def `flatmap function` = {
    check(
      Test.testSuccess(
        MonadSection.monadFlatmap _,
        List(1, 1, 2, 2, 3, 3) :: HNil
      )
    )
  }

  def `ifM function` = {
    check(
      Test.testSuccess(
        MonadSection.monadIfm _,
        Option("truthy") :: List(1, 2, 3, 4, 1, 2) :: HNil
      )
    )
  }

  def `monad composition` = {
    check(
      Test.testSuccess(
        MonadSection.monadComposition _,
        List(Option(42)) :: HNil
      )
    )
  }
}
