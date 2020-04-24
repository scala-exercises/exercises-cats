/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2020 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package catslib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class EvalSpec extends RefSpec with Checkers {

  def `eager eval (now) is evaluated` = {
    check(
      Test.testSuccess(
        EvalSection.nowEval _,
        List(1, 2, 3) :: HNil
      )
    )
  }

  def `later eval is only evaluated` = {
    check(
      Test.testSuccess(
        EvalSection.laterEval _,
        List(1, 2) :: 1 :: HNil
      )
    )
  }

  def `always eval is only evaluated` = {
    check(
      Test.testSuccess(
        EvalSection.alwaysEval _,
        List(1, 2, 3, 4) :: 4 :: 5 :: HNil
      )
    )
  }

  def `defer eval chaining with flatMap` = {
    check(
      Test.testSuccess(
        EvalSection.deferEval _,
        List(0, 0, 0) :: HNil
      )
    )
  }
}
