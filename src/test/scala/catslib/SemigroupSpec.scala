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
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.refspec.RefSpec
import shapeless.HNil

class SemigroupSpec extends RefSpec with Checkers {
  def `has a combine operation` = {
    check(
      Test.testSuccess(
        SemigroupSection.semigroupCombine _,
        3 :: List(1, 2, 3, 4, 5, 6) :: Option(3) :: Option(1) :: HNil
      )
    )
  }

  def `has an advanced combine operation` = {
    check(
      Test.testSuccess(
        SemigroupSection.semigroupCombineComplex _,
        67 :: HNil
      )
    )
  }

  def `composes with other semigroups` = {
    check(
      Test.testSuccess(
        SemigroupSection.composingSemigroups _,
        Map("bar" -> 11) :: HNil
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
