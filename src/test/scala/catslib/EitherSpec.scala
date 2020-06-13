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

import cats.implicits._
import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.refspec.RefSpec
import shapeless.HNil

class EitherSpec extends RefSpec with Checkers {
  def `is right biased` = {
    check(
      Test.testSuccess(
        EitherSection.eitherMapRightBias _,
        Either.right[String, Int](6) :: Either.left[String, Int]("Something went wrong") :: HNil
      )
    )
  }

  def `has a Monad implementation` = {
    check(
      Test.testSuccess(
        EitherSection.eitherMonad _,
        Either.right[String, Int](6) :: Either.left[String, Int]("Something went wrong") :: HNil
      )
    )
  }

  def `Either instead of exceptions` = {
    check(
      Test.testSuccess(
        EitherSection.eitherStyleParse _,
        false :: true :: HNil
      )
    )
  }

  def `Either composes nicely` = {
    check(
      Test.testSuccess(
        EitherSection.eitherComposition _,
        false :: true :: false :: HNil
      )
    )
  }

  def `Either can carry exceptions on the left` = {
    check(
      Test.testSuccess(
        EitherSection.eitherExceptions _,
        "Got reciprocal: 0.5" :: HNil
      )
    )
  }

  def `Either can carry a value of an error ADT on the left` = {
    check(
      Test.testSuccess(
        EitherSection.eitherErrorsAsAdts _,
        "Got reciprocal: 0.5" :: HNil
      )
    )
  }

  def `Either in the large` = {
    check(
      Test.testSuccess(
        EitherSection.eitherInTheLarge _,
        Either.right[String, Int](42) :: Either
          .left[String, Int]("Hello") :: Either.left[String, Int]("olleH") :: HNil
      )
    )
  }

  def `Either with exceptions` = {
    check(
      Test.testSuccess(
        EitherSection.eitherWithExceptions _,
        false :: true :: HNil
      )
    )
  }

  def `Either syntax` = {
    check(
      Test.testSuccess(
        EitherSection.eitherSyntax _,
        Either.right[String, Int](42) :: HNil
      )
    )
  }
}
