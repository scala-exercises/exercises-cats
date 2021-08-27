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

import cats.kernel.Semigroup
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec

/**
 * A semigroup for some given type A has a single operation (which we will call `combine`), which
 * takes two values of type A, and returns a value of type A. This operation must be guaranteed to
 * be associative. That is to say that:
 *
 * {{{
 * ((a combine b) combine c)
 * }}}
 *
 * must be the same as
 *
 * {{{
 * (a combine (b combine c))
 * }}}
 *
 * for all possible values of a,b,c.
 *
 * There are instances of `Semigroup` defined for many types found in the scala common library. For
 * example, `Int` values are combined using addition by default but multiplication is also
 * associative and forms another `Semigroup`.
 *
 * {{{
 * import cats.Semigroup
 * }}}
 *
 * @param name
 *   semigroup
 */
object SemigroupSection
    extends AnyFlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

  /**
   * Now that you've learned about the `Semigroup` instance for `Int` try to guess how it works in
   * the following examples:
   */
  def semigroupCombine(res0: Int, res1: List[Int], res2: Option[Int], res3: Option[Int]) = {
    import cats.implicits._

    Semigroup[Int].combine(1, 2) should be(res0)
    Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) should be(res1)
    Semigroup[Option[Int]].combine(Option(1), Option(2)) should be(res2)
    Semigroup[Option[Int]].combine(Option(1), None) should be(res3)
  }

  /**
   * And now try a slightly more complex combination:
   */
  def semigroupCombineComplex(res0: Int) = {
    import cats.implicits._

    Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6) should be(res0)

  }

  /**
   * Many of these types have methods defined directly on them, which allow for such combining, e.g.
   * `++` on List, but the value of having a `Semigroup` type class available is that these compose,
   * so for instance, we can say
   *
   * {{{
   * Map("foo" -> Map("bar" -> 5)).combine(Map("foo" -> Map("bar" -> 6), "baz" -> Map()))
   * Map("foo" -> List(1, 2)).combine(Map("foo" -> List(3,4), "bar" -> List(42)))
   * }}}
   *
   * which is far more likely to be useful than
   *
   * {{{
   * Map("foo" -> Map("bar" -> 5)) ++  Map("foo" -> Map("bar" -> 6), "baz" -> Map())
   * Map("foo" -> List(1, 2)) ++ Map("foo" -> List(3,4), "bar" -> List(42))
   * }}}
   *
   * since the first version uses the Semigroup's `combine` operation, it will merge the map's
   * values with `combine`.
   */
  def composingSemigroups(res0: Map[String, Int]) = {
    import cats.implicits._

    val aMap        = Map("foo" -> Map("bar" -> 5))
    val anotherMap  = Map("foo" -> Map("bar" -> 6))
    val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)

    combinedMap.get("foo") should be(Some(res0))
  }

  /**
   * There is inline syntax available for `Semigroup`. Here we are following the convention from
   * scalaz, that `|+|` is the operator from `Semigroup`.
   *
   * You'll notice that instead of declaring `one` as `Some(1)`, I chose `Option(1)`, and I added an
   * explicit type declaration for `n`. This is because there aren't type class instances for Some
   * or None, but for Option.
   */
  def semigroupSpecialSyntax(
      res0: Option[Int],
      res1: Option[Int],
      res2: Option[Int],
      res3: Option[Int]
  ) = {
    import cats.implicits._

    val one: Option[Int] = Option(1)
    val two: Option[Int] = Option(2)
    val n: Option[Int]   = None

    one |+| two should be(res0)
    n |+| two should be(res1)
    n |+| n should be(res2)
    two |+| n should be(res3)
  }
}
