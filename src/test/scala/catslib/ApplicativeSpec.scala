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

class ApplicativeSpec extends RefSpec with Checkers {
  def `pure method` = {
    check(
      Test.testSuccess(
        ApplicativeSection.pureMethod _,
        Option(1) :: List(1) :: HNil
      )
    )
  }

  def `applicative composition` = {
    check(
      Test.testSuccess(
        ApplicativeSection.applicativeComposition _,
        List(Option(1)) :: HNil
      )
    )
  }

  def `applicative and monad` = {
    check(
      Test.testSuccess(
        ApplicativeSection.applicativesAndMonads _,
        Option(1) :: Option(1) :: HNil
      )
    )
  }
}
