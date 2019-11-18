/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
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
