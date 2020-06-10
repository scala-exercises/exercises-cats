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

class TraverseSpec extends RefSpec with Checkers {
  def `traverseu function with either` = {
    check(
      Test.testSuccess(
        TraverseSection.traverseuFunction _,
        List(1, 2, 3) :: true :: HNil
      )
    )
  }

  def `traverseu function with validated` = {
    check(
      Test.testSuccess(
        TraverseSection.traverseuValidated _,
        true :: HNil
      )
    )
  }

  def `sequencing effects` = {
    val aNone: Option[List[Int]] = None

    check(
      Test.testSuccess(
        TraverseSection.sequencing _,
        Option(List(1, 2, 3)) :: aNone :: HNil
      )
    )
  }

  def `traversing for effects` = {
    val aNone: Option[Unit] = None

    check(
      Test.testSuccess(
        TraverseSection.traversingForEffects _,
        Option(()) :: aNone :: HNil
      )
    )
  }
}
