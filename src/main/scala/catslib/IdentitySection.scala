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

import cats._
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec

/**
 * The identity monad can be seen as the ambient monad that encodes the
 * effect of having no effect. It is ambient in the sense that plain pure
 * values are values of `Id`.
 *
 * It is encoded as:
 *
 * {{{
 * type Id[A] = A
 * }}}
 *
 * That is to say that the type Id[A] is just a synonym for A.  We can
 * freely treat values of type `A` as values of type `Id[A]`, and
 * vice-versa.
 *
 * {{{
 * import cats._
 *
 * val x: Id[Int] = 1
 * val y: Int = x
 * }}}
 *
 * @param name identity
 */
object IdentitySection
    extends AnyFlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

  /**
   * We can freely compare values of `Id[T]` with unadorned
   * values of type `T`.
   */
  def identityType(res0: Int) = {
    val anId: Id[Int] = 42
    anId should be(res0)
  }

  /**
   * Using this type declaration, we can treat our Id type constructor as a
   * `Monad` and as a `Comonad`. The `pure`
   * method, which has type `A => Id[A]` just becomes the identity
   * function.  The `map` method from `Functor` just becomes function
   * application:
   *
   * {{{
   * import cats.Functor
   *
   * val one: Int = 1
   * Functor[Id].map(one)(_ + 1)
   * }}}
   */
  def pureIdentity(res0: Int) =
    Applicative[Id].pure(42) should be(res0)

  /**
   * Compare the signatures of `map` and `flatMap` and `coflatMap`:
   *
   * {{{
   * def map[A, B](fa: Id[A])(f: A => B): Id[B]
   * def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B]
   * def coflatMap[A, B](a: Id[A])(f: Id[A] => B): Id[B]
   * }}}
   *
   * You'll notice that in the flatMap signature, since `Id[B]` is the same
   * as `B` for all B, we can rewrite the type of the `f` parameter to be
   * `A => B` instead of `A => Id[B]`, and this makes the signatures of the
   * two functions the same, and, in fact, they can have the same
   * implementation, meaning that for `Id`, `flatMap` is also just function
   * application:
   *
   * {{{
   * import cats.Monad
   *
   * val one: Int = 1
   * Monad[Id].map(one)(_ + 1)
   * Monad[Id].flatMap(one)(_ + 1)
   * }}}
   */
  def idComonad(res0: Int) = {
    val fortytwo: Int = 42
    Comonad[Id].coflatMap(fortytwo)(_ + 1) should be(res0)
  }
}
