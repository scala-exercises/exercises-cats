/*
 * scala-exercises - exercises-cats
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package catslib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.prop.Checkers
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

  def `validation with xor` = {
    check(
      Test.testSuccess(
        ValidatedSection.validatedAsXor _,
        false :: true :: HNil
      )
    )
  }
}
