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
        Option("truthy") :: List(1, 2, 3, 4, 1, 2) :: List(3, 4, 1, 2, 3, 4) :: HNil
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
