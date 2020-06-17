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

import cats._
import cats.implicits._

/**
 * `Applicative` extends `Apply` by adding a single method, `pure`:
 *
 * {{{
 * def pure[A](x: A): F[A]
 * }}}
 *
 * @param name applicative
 */
object ApplicativeSection
    extends AnyFlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

  /**
   * This method takes any value and returns the value in the context of
   * the functor. For many familiar functors, how to do this is
   * obvious. For `Option`, the `pure` operation wraps the value in
   * `Some`. For `List`, the `pure` operation returns a single element
   * `List`:
   */
  def pureMethod(res0: Option[Int], res1: List[Int]) = {
    import cats._
    import cats.implicits._

    Applicative[Option].pure(1) should be(res0)
    Applicative[List].pure(1) should be(res1)
  }

  /**
   * Like `Functor` and `Apply`, `Applicative`
   * functors also compose naturally with each other. When
   * you compose one `Applicative` with another, the resulting `pure`
   * operation will lift the passed value into one context, and the result
   * into the other context:
   */
  def applicativeComposition(res0: List[Option[Int]]) =
    (Applicative[List] compose Applicative[Option]).pure(1) should be(res0)

  /**
   * = Applicative Functors & Monads =
   *
   * `Applicative` is a generalization of `Monad`, allowing expression
   * of effectful computations in a pure functional way.
   *
   * `Applicative` is generally preferred to `Monad` when the structure of a
   * computation is fixed a priori. That makes it possible to perform certain
   * kinds of static analysis on applicative values.
   */
  def applicativesAndMonads(res0: Option[Int], res1: Option[Int]) = {
    Monad[Option].pure(1) should be(res0)
    Applicative[Option].pure(1) should be(res1)
  }
}
