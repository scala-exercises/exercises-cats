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

class MonoidSpec extends RefSpec with Checkers {
  def `has a empty operation` = {
    check(
      Test.testSuccess(
        MonoidSection.monoidEmpty _,
        "" :: "abc" :: "" :: HNil
      )
    )
  }

  def `advantages of using monoid operations` = {
    val aMap: Map[String, Int]       = Map("a" -> 4, "b" -> 2)
    val anotherMap: Map[String, Int] = Map()

    check(
      Test.testSuccess(
        MonoidSection.monoidAdvantage _,
        aMap :: anotherMap :: HNil
      )
    )
  }

  def `accumulating with a monoid on foldMap` = {
    check(
      Test.testSuccess(
        MonoidSection.monoidFoldmap _,
        15 :: "12345" :: HNil
      )
    )
  }

  def `accumulating with a tuple on foldMap` = {
    check(
      Test.testSuccess(
        MonoidSection.tupleMonoid _,
        (15, "12345") :: HNil
      )
    )
  }
}
