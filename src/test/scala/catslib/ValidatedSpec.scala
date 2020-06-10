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

class ValidatedSpec extends RefSpec with Checkers {
  def `with no errors` = {
    check(
      Test.testSuccess(
        ValidatedSection.noErrors _,
        true :: "127.0.0.1" :: 1337 :: HNil
      )
    )
  }

  def `with accumulating errors` = {
    check(
      Test.testSuccess(
        ValidatedSection.someErrors _,
        false :: true :: HNil
      )
    )
  }

  def `sequential validation` = {
    check(
      Test.testSuccess(
        ValidatedSection.sequentialValidation _,
        false :: true :: HNil
      )
    )
  }

  def `validation with either` = {
    check(
      Test.testSuccess(
        ValidatedSection.validatedAsEither _,
        false :: true :: HNil
      )
    )
  }
}
