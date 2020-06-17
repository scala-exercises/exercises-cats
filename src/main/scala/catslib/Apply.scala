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

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import ApplyHelpers._

import cats._
import cats.implicits._

/**
 * `Apply` extends the `Functor` type class (which features the familiar `map`
 * function) with a new function `ap`. The `ap` function is similar to `map`
 * in that we are transforming a value in a context (a context being the `F` in `F[A]`;
 * a context can be `Option`, `List` or `Future` for example).
 * However, the difference between `ap` and `map` is that for `ap` the function that
 * takes care of the transformation is of type `F[A => B]`, whereas for `map` it is `A => B`:
 *
 * Here are the implementations of `Apply` for the `Option` and `List` types:
 * {{{
 * import cats._
 *
 * implicit val optionApply: Apply[Option] = new Apply[Option] {
 * def ap[A, B](f: Option[A => B])(fa: Option[A]): Option[B] =
 *  fa.flatMap (a => f.map (ff => ff(a)))
 *
 * def map[A,B](fa: Option[A])(f: A => B): Option[B] = fa map f
 *
 * def product[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
 *  fa.flatMap(a => fb.map(b => (a, b)))
 * }
 *
 * implicit val listApply: Apply[List] = new Apply[List] {
 * def ap[A, B](f: List[A => B])(fa: List[A]): List[B] =
 *  fa.flatMap (a => f.map (ff => ff(a)))
 *
 * def map[A,B](fa: List[A])(f: A => B): List[B] = fa map f
 *
 * def product[A, B](fa: List[A], fb: List[B]): List[(A, B)] =
 *  fa.zip(fb)
 * }
 * }}}
 *
 * @param name apply
 */
object ApplySection extends AnyFlatSpec with Matchers with org.scalaexercises.definitions.Section {

  /**
   * = map =
   *
   * Since `Apply` extends `Functor`, we can use the `map` method from `Functor`:
   */
  def applyExtendsFunctor(res0: Option[String], res1: Option[Int], res2: Option[Int]) = {
    import cats.implicits._

    val intToString: Int => String = _.toString
    val double: Int => Int         = _ * 2
    val addTwo: Int => Int         = _ + 2

    Apply[Option].map(Some(1))(intToString) should be(res0)
    Apply[Option].map(Some(1))(double) should be(res1)
    Apply[Option].map(None)(addTwo) should be(res2)
  }

  /**
   * = compose =
   *
   * And like functors, `Apply` instances also compose:
   */
  def applyComposes(res0: List[Option[Int]]) = {
    val listOpt = Apply[List] compose Apply[Option]
    val plusOne = (x: Int) => x + 1
    listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3))) should be(res0)
  }

  /**
   * = ap =
   *
   * The `ap` method is a method that `Functor` does not have:
   */
  def applyAp(
      res0: Option[String],
      res1: Option[Int],
      res2: Option[Int],
      res3: Option[Int],
      res4: Option[Int]
  ) = {
    Apply[Option].ap(Some(intToString))(Some(1)) should be(res0)
    Apply[Option].ap(Some(double))(Some(1)) should be(res1)
    Apply[Option].ap(Some(double))(None) should be(res2)
    Apply[Option].ap(None)(Some(1)) should be(res3)
    Apply[Option].ap(None)(None) should be(res4)
  }

  /**
   * = ap2, ap3, etc =
   *
   * `Apply` also offers variants of `ap`. The functions `apN` (for `N` between `2` and `22`)
   * accept `N` arguments where `ap` accepts `1`.
   *
   * Note that if any of the arguments of this example is `None`, the
   * final result is `None` as well.  The effects of the context we are operating on
   * are carried through the entire computation:
   */
  def applyApn(res0: Option[Int], res1: Option[Int], res2: Option[Int]) = {
    val addArity2 = (a: Int, b: Int) => a + b
    Apply[Option].ap2(Some(addArity2))(Some(1), Some(2)) should be(res0)
    Apply[Option].ap2(Some(addArity2))(Some(1), None) should be(res1)

    val addArity3 = (a: Int, b: Int, c: Int) => a + b + c
    Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3)) should be(res2)
  }

  /**
   * = map2, map3, etc =
   *
   * Similarly, `mapN` functions are available:
   */
  def applyMapn(res0: Option[Int], res1: Option[Int]) = {
    Apply[Option].map2(Some(1), Some(2))(addArity2) should be(res0)

    Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3) should be(res1)
  }

  /**
   * = tuple2, tuple3, etc =
   *
   * Similarly, `tupleN` functions are available:
   */
  def applyTuplen(res0: Option[(Int, Int)], res1: Option[(Int, Int, Int)]) = {
    Apply[Option].tuple2(Some(1), Some(2)) should be(res0)
    Apply[Option].tuple3(Some(1), Some(2), Some(3)) should be(res1)
  }

  /**
   * = apply builder syntax =
   *
   * In order to use functions `apN`, `mapN` and `tupleN` *infix*,
   * import `cats.implicits._`.
   */
  def applyBuilderSyntax(
      res0: Option[Int],
      res1: Option[Int],
      res2: Option[Int],
      res3: Option[Int],
      res4: Option[(Int, Int)],
      res5: Option[(Int, Int, Int)]
  ) = {
    import cats.implicits._
    val option2 = (Option(1), Option(2))
    val option3 = (option2._1, option2._2, Option.empty[Int])

    option2 mapN addArity2 should be(res0)
    option3 mapN addArity3 should be(res1)

    option2 apWith Some(addArity2) should be(res2)
    option3 apWith Some(addArity3) should be(res3)

    option2.tupled should be(res4)
    option3.tupled should be(res5)
  }

}
