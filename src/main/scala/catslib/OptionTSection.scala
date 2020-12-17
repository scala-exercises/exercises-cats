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
import cats.data.OptionT
import cats.implicits._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * OptionT is a Monad Transformer that has two type parameters F and A.
 * F is the wrapping Monad and A is type inside Option. As a result, OptionT[F[_], A] is a light wrapper on an F[Option[A]].
 * As OptionT is also a monad, it can be used in a for-comprehension and be more convenient to work with than using F[Option[A]] directly.
 *
 * @param name OptionT
 */
object OptionTSection
    extends AnyFlatSpec
    with Matchers
    with ScalaFutures
    with org.scalaexercises.definitions.Section {

  implicit val futureOptionEq = new Eq[Future[Option[String]]] {
    def eqv(x: Future[Option[String]], y: Future[Option[String]]): Boolean =
      x.futureValue.eqv(y.futureValue)
  }

  /**
   * If you have only an A and you wish to lift it into an OptionT[F,A], assuming you
   * have an Applicative instance for F you can use `some` which is an alias for `pure`.
   * There also exists a none method which can be used to create an OptionT[F,A],
   * where the Option wrapped A type is actually a None:
   *
   * {{{
   * import scala.concurrent.Future
   * import scala.concurrent.ExecutionContext.Implicits.global
   * import cats.implicits._ //imports implicit Applicative[Future]
   * }}}
   */
  def pureOption(spanishGreetValue: String, englishGreetValue: String, failedGreetValue: String) = {
    //given
    val englishGreetOpT: OptionT[Id, String]     = OptionT.some[Id]("Hello")
    val spanishGreetOpT: OptionT[Future, String] = OptionT.pure[Future]("Hola")
    val failedGreetOpT: OptionT[Future, String]  = OptionT.none

    //when
    val englishGreet: Id[String]     = englishGreetOpT.getOrElse("")
    val spanishGreet: Future[String] = spanishGreetOpT.getOrElse("")
    val failedGreet: Future[String]  = failedGreetOpT.getOrElse("")

    //then
    englishGreet shouldBe a[Id[_]]
    spanishGreet shouldBe a[Future[_]]
    englishGreet shouldBe (englishGreetValue: String)
    spanishGreet.futureValue shouldBe (spanishGreetValue: String)
    failedGreet.futureValue shouldBe (failedGreetValue: String)
  }

  /**
   * Sometimes you may have an Option[A] and/or F[A] and want to lift them into an OptionT[F, A].
   * For this purpose OptionT exposes two useful methods, namely fromOption and liftF,
   * and the standard apply respectively. E.g.:
   * {{{
   * val greetingFO: Future[Option[String]] = Future.successful(Some("Hello"))
   *
   * val firstnameF: Future[String] = Future.successful("Jane")
   *
   * val lastnameO: Option[String] = Some("Doe")
   * }}}
   */
  def fromOptionT(
      maybeFrenchGreet: Option[String],
      maybeItalianGreet: Option[String],
      maybeFailedGreet: Option[String]
  ) = {
    //given
    val frenchGreetFO: Future[Option[String]] = Future.successful(Option("Bonjour"))
    val italianGreetF: Future[String]         = Future.successful("Ciao")
    val failedGreetO: Option[String]          = None

    //when
    val frenchGreetOpT: OptionT[Future, String]  = OptionT(frenchGreetFO)
    val italianGreetOpT: OptionT[Future, String] = OptionT.liftF(italianGreetF)
    val failedGreetOpT: OptionT[Future, String]  = OptionT.fromOption(failedGreetO)

    //then
    assert(frenchGreetOpT === OptionT.fromOption[Future](maybeFrenchGreet))
    assert(italianGreetOpT === OptionT.fromOption[Future](maybeItalianGreet))
    assert(failedGreetOpT === OptionT.fromOption[Future](maybeFailedGreet))
  }

  /**
   * As you can see, the implementations of all of these variations are very similar.
   * We want to call the Option operation (map, filter, filterNot, getOrElse),
   * but since our Option is wrapped in a Future, we first need to map over the Future.
   * OptionT can help remove some of this boilerplate.
   * It exposes methods that look like those on Option,
   * but it handles the outer map call on the Future so we don’t have to:
   *
   * {{{
   * //operating with flattened Future[Option[String]
   * import scala.concurrent.Future
   * import scala.concurrent.ExecutionContext.Implicits.global
   * val customGreeting: Future[Option[String]] = Future.successful(Some("welcome back, Lola"))
   * val excitedGreeting: Future[Option[String]] = customGreeting.map(_.map(_ + "!"))
   * val hasWelcome: Future[Option[String]] = customGreeting.map(_.filter(_.contains("welcome")))
   * val noWelcome: Future[Option[String]] = customGreeting.map(_.filterNot(_.contains("welcome")))
   * val withFallback: Future[String] = customGreeting.map(_.getOrElse("hello, there!"))
   *
   * //operating with transformer OptionT[Future, String]
   * import cats.data.OptionT
   * import cats.implicits._
   * val customGreetingT: OptionT[Future, String] = OptionT(customGreeting)
   * val excitedGreeting: OptionT[Future, String] = customGreetingT.map(_ + "!")
   * val withWelcome: OptionT[Future, String] = customGreetingT.filter(_.contains("welcome"))
   * val noWelcome: OptionT[Future, String] = customGreetingT.filterNot(_.contains("welcome")) //None
   * val withFallback: Future[String] = customGreetingT.getOrElse("hello, there!")
   * }}}
   */
  def optionTMethods(
      maybeGreet: List[Option[String]],
      maybeGreetWorld: List[Option[String]],
      maybeGreetH: List[Option[String]],
      maybeGreetGuten: List[Option[String]],
      maybeGreetGutenTag: List[String],
      maybeGreetGutenAbend: List[String]
  ) = {
    //given
    val germanGreetingsLO: List[Option[String]] =
      List("Hallo".some, "Hi".some, "Guten Morgen".some, none[String])
    val germanGreetingsT: OptionT[List, String] = OptionT(germanGreetingsLO)

    //when/then (optionT vs flatten)
    //map
    germanGreetingsT.map(_ + "!") shouldBe OptionT(maybeGreet: List[Option[String]])
    germanGreetingsLO.map(_.map(_ + " World!")) shouldBe OptionT(
      maybeGreetWorld: List[Option[String]]
    ).value
    //filter
    germanGreetingsT.filter(_.contains("H")) shouldBe OptionT(maybeGreetH: List[Option[String]])
    germanGreetingsLO.map(_.filter(_.contains("Guten"))) shouldBe OptionT(
      maybeGreetGuten: List[Option[String]]
    ).value
    //getOrElse
    germanGreetingsT.getOrElse("Guten Tag") shouldBe (maybeGreetGutenTag: List[String])
    germanGreetingsLO.map(_.getOrElse("Guten Abend")) shouldBe (maybeGreetGutenAbend: List[String])
    //...
  }

  /**
   * OptionT is a Monad, so it has a flatMap method which can be used in a for-comprehension.
   *
   * {{{
   * val greetingFO: Future[Option[String]] = Future.successful(Some("Hello"))
   *
   * val firstnameF: Future[String] = Future.successful("Jane")
   *
   * val lastnameO: Option[String] = Some("Doe")
   *
   * val ot: OptionT[Future, String] = for {
   *   g <- OptionT(greetingFO)
   *   f <- OptionT.liftF(firstnameF)
   *   l <- OptionT.fromOption[Future](lastnameO)
   * } yield s"$g $f $l"
   *
   * val result: Future[Option[String]] = ot.value // Future(Some("Hello Jane Doe"))
   * }}}
   */
  def forComprehension(maybeOpResult: Option[Double]) = {
    //given
    val initialNumber: Future[Option[Double]] = Future(0.0.some)

    //when
    val opResult: OptionT[Future, Double] = for {
      a <- OptionT(initialNumber).map(_ + 2)
      b <- OptionT.liftF(Future(a)).map(_ * 6)
      c <- OptionT.liftF(Future(b)).map(_ / 8)
    } yield c

    //then
    opResult.value.futureValue shouldBe (maybeOpResult: Option[Double])

  }
}
