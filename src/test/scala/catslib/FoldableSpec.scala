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

class FoldableSpec extends RefSpec with Checkers {
  def `foldLeft function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableFoldLeft _,
        6 :: "abc" :: HNil
      )
    )
  }

  def `foldRight function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableFoldRight _,
        6 :: HNil
      )
    )
  }

  def `fold function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableFold _,
        "abc" :: 6 :: HNil
      )
    )
  }

  def `foldMap function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableFoldMap _,
        3 :: "123" :: HNil
      )
    )
  }

  def `find function` = {
    val aNone: Option[Int] = None

    check(
      Test.testSuccess(
        FoldableSection.foldableFind _,
        Option(3) :: aNone :: HNil
      )
    )
  }

  def `exists function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableExists _,
        true :: false :: HNil
      )
    )
  }

  def `forall function` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableForall _,
        true :: false :: HNil
      )
    )
  }

  def `foldK function` = {
    val aNone: Option[Unit] = None

    check(
      Test.testSuccess(
        FoldableSection.foldableFoldk _,
        List(1, 2, 3, 4, 5) :: Option("two") :: HNil
      )
    )
  }

  def `tolist function` = {
    val emptyList: List[Int] = Nil

    check(
      Test.testSuccess(
        FoldableSection.foldableTolist _,
        List(1, 2, 3) :: List(42) :: emptyList :: HNil
      )
    )
  }

  def `filter function` = {
    val emptyList: List[Int] = Nil

    check(
      Test.testSuccess(
        FoldableSection.foldableFilter _,
        List(1, 2) :: emptyList :: HNil
      )
    )
  }

  def `traverse_ function` = {
    val aNone: Option[Unit] = None

    check(
      Test.testSuccess(
        FoldableSection.foldableTraverse _,
        Option(()) :: aNone :: HNil
      )
    )
  }

  def `compose foldables` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableCompose _,
        10 :: "123" :: HNil
      )
    )
  }

  def `foldable methods` = {
    check(
      Test.testSuccess(
        FoldableSection.foldableMethods _,
        false :: List(2, 3) :: List(1) :: HNil
      )
    )
  }
}
