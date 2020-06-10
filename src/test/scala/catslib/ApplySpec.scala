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

class ApplySpec extends RefSpec with Checkers {
  def `extends functor` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyExtendsFunctor _,
        Option("1") :: Option(2) :: theNone :: HNil
      )
    )
  }

  def `is composable` = {
    val result: List[Option[Int]] = List(Some(2), None, Some(4))

    check(
      Test.testSuccess(
        ApplySection.applyComposes _,
        result :: HNil
      )
    )
  }

  def `ap method` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyAp _,
        Option("1") :: Option(2) :: theNone :: theNone :: theNone :: HNil
      )
    )
  }

  def `apN method` = {
    val theNone: Option[Int] = None

    check(
      Test.testSuccess(
        ApplySection.applyApn _,
        Option(3) :: theNone :: Option(6) :: HNil
      )
    )
  }

  def `mapN method` = {
    check(
      Test.testSuccess(
        ApplySection.applyMapn _,
        Option(3) :: Option(6) :: HNil
      )
    )
  }

  def `tupleN method` = {
    check(
      Test.testSuccess(
        ApplySection.applyTuplen _,
        Option((1, 2)) :: Option((1, 2, 3)) :: HNil
      )
    )
  }

  def `builder syntax` = {
    val aNone: Option[Int]                   = None
    val anotherNone: Option[(Int, Int, Int)] = None

    check(
      Test.testSuccess(
        ApplySection.applyBuilderSyntax _,
        Option(3) :: aNone :: Option(3) :: aNone :: Option((1, 2)) :: anotherNone :: HNil
      )
    )
  }
}
