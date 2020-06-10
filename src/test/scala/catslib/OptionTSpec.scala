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

class OptionTSpec extends RefSpec with Checkers {

  def `Option pure value` = {
    check(
      Test.testSuccess(
        OptionTSection.pureOption _,
        "Hola" :: "Hello" :: "" :: HNil
      )
    )
  }

  def `From option` = {
    check(
      Test.testSuccess(
        OptionTSection.fromOptionT _,
        Option("Bonjour") :: Option("Ciao") :: Option.empty[String] :: HNil
      )
    )
  }

  def `OptionT methods` = {
    check(
      Test.testSuccess(
        OptionTSection.optionTMethods _,
        List(Option("Hallo!"), Option("Hi!"), Option("Guten Morgen!"), Option.empty[String]) ::
          (List(
            Option("Hallo World!"),
            Option("Hi World!"),
            Option("Guten Morgen World!"),
            Option.empty[String]
          )) ::
          List(Option("Hallo"), Option("Hi"), Option.empty[String], Option.empty[String]) ::
          List(
            Option.empty[String],
            Option.empty[String],
            Option("Guten Morgen"),
            Option.empty[String]
          ) ::
          List("Hallo", "Hi", "Guten Morgen", "Guten Tag") ::
          List("Hallo", "Hi", "Guten Morgen", "Guten Abend") :: HNil
      )
    )
  }

  def `For comprehension` = {
    check(
      Test.testSuccess(
        OptionTSection.forComprehension _,
        Option(1.5) :: HNil
      )
    )
  }
}
