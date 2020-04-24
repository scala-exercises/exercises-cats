/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2020 47 Degrees, LLC. <http://www.47deg.com>
 *
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
