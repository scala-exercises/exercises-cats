/*
 * Copyright 2016-2020 47 Degrees Open Source <https://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        4 :: List(1, 2, 3, 4) :: 5 :: HNil
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
