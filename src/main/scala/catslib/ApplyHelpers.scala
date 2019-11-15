/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package catslib

object ApplyHelpers {
  val intToString: Int ⇒ String = _.toString
  val double: Int ⇒ Int         = _ * 2
  val addTwo: Int ⇒ Int         = _ + 2
  val addArity2                 = (a: Int, b: Int) ⇒ a + b
  val addArity3                 = (a: Int, b: Int, c: Int) ⇒ a + b + c
}
