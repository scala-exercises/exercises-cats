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

import cats.implicits._

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec

object EitherStyle {
  def parse(s: String): Either[NumberFormatException, Int] =
    if (s.matches("-?[0-9]+")) Either.right(s.toInt)
    else Either.left(new NumberFormatException(s"${s} is not a valid integer."))

  def reciprocal(i: Int): Either[IllegalArgumentException, Double] =
    if (i == 0) Either.left(new IllegalArgumentException("Cannot take reciprocal of 0."))
    else Either.right(1.0 / i)

  def stringify(d: Double): String = d.toString

  def magic(s: String): Either[Exception, String] =
    parse(s).flatMap(reciprocal).map(stringify)
}

object EitherStyleWithAdts {
  sealed abstract class Error
  final case class NotANumber(string: String) extends Error
  final case object NoZeroReciprocal          extends Error

  def parse(s: String): Either[Error, Int] =
    if (s.matches("-?[0-9]+")) Either.right(s.toInt)
    else Either.left(NotANumber(s))

  def reciprocal(i: Int): Either[Error, Double] =
    if (i == 0) Either.left(NoZeroReciprocal)
    else Either.right(1.0 / i)

  def stringify(d: Double): String = d.toString

  def magic(s: String): Either[Error, String] =
    parse(s).flatMap(reciprocal).map(stringify)
}

/**
 * In day-to-day programming, it is fairly common to find ourselves writing functions that
 * can fail. For instance, querying a service may result in a connection issue, or some
 * unexpected JSON response.
 *
 * To communicate these errors it has become common practice to throw exceptions. However,
 * exceptions are not tracked in any way, shape, or form by the Scala compiler. To see
 * what kind of exceptions (if any) a function may throw, we have to dig through the source code.
 * Then to handle these exceptions, we have to make sure we catch them at the call site.
 * This all becomes even more unwieldy when we try to compose exception-throwing procedures.
 *
 * {{{
 * val throwsSomeStuff: Int => Double = ???
 *
 * val throwsOtherThings: Double => String = ???
 *
 * val moreThrowing: String => List[Char] = ???
 *
 * val magic = throwsSomeStuff.andThen(throwsOtherThings).andThen(moreThrowing)
 * }}}
 *
 * Assume we happily throw exceptions in our code. Looking at the types, any of those functions
 * can throw any number of exceptions, we don't know. When we compose, exceptions from any of
 * the constituent functions can be thrown. Moreover, they may throw the same kind of exception
 * (e.g. `IllegalArgumentException`) and thus it gets tricky tracking exactly where that
 * exception came from.
 *
 * How then do we communicate an error? By making it explicit in the data type we return.
 *
 * =`Either` vs `Validated`=
 *
 * In general, `Validated` is used to accumulate errors, while `Either` is used to short-circuit a computation upon the
 * first error. For more information, see the
 * [[https://typelevel.org/cats/datatypes/validated.html#validated-vs-either Validated vs Either]]
 * section of the `Validated` documentation.
 *
 * @param name either
 */
object EitherSection extends AnyFlatSpec with Matchers with org.scalaexercises.definitions.Section {

  /**
   * More often than not we want to just bias towards one side and call it a day - by convention,
   * the right side is most often chosen.
   */
  def eitherMapRightBias(res0: Either[String, Int], res1: Either[String, Int]) = {

    val right: Either[String, Int] = Either.right(5)
    right.map(_ + 1) should be(res0)

    val left: Either[String, Int] = Either.left("Something went wrong")
    left.map(_ + 1) should be(res1)
  }

  /**
   * Because `Either` is right-biased, it is possible to define a `Monad` instance for it.
   *
   * Since we only ever want the computation to continue in the case of `Right` (as captured
   * by the right-bias nature), we fix the left type parameter and leave the right one free.
   *
   * {{{
   * import cats.implicits._
   * import cats.Monad
   *
   * implicit def eitherMonad[Err]: Monad[Either[Err, *]] =
   * new Monad[Either[Err, *]] {
   *  def flatMap[A, B](fa: Either[Err, A])(f: A => Either[Err, B]): Either[Err, B] =
   *    fa.flatMap(f)
   *
   *  def pure[A](x: A): Either[Err, A] = Either.right(x)
   * }
   * }}}
   *
   * So the `flatMap` method is right-biased:
   */
  def eitherMonad(res0: Either[String, Int], res1: Either[String, Int]) = {

    val right: Either[String, Int] = Either.right(5)
    right.flatMap(x => Either.right(x + 1)) should be(res0)

    val left: Either[String, Int] = Either.left("Something went wrong")
    left.flatMap(x => Either.right(x + 1)) should be(res1)
  }

  /**
   * = Using `Either` instead of exceptions =
   *
   * As a running example, we will have a series of functions that will parse a string into an integer,
   * take the reciprocal, and then turn the reciprocal into a string.
   *
   * In exception-throwing code, we would have something like this:
   *
   * {{{
   * object ExceptionStyle {
   * def parse(s: String): Int =
   *  if (s.matches("-?[0-9]+")) s.toInt
   *  else throw new NumberFormatException(s"${s} is not a valid integer.")
   *
   * def reciprocal(i: Int): Double =
   *  if (i == 0) throw new IllegalArgumentException("Cannot take reciprocal of 0.")
   *  else 1.0 / i
   *
   * def stringify(d: Double): String = d.toString
   *
   * }
   * }}}
   *
   * Instead, let's make the fact that some of our functions can fail explicit in the return type.
   *
   * {{{
   * object EitherStyle {
   * def parse(s: String): Either[NumberFormatException, Int] =
   *  if (s.matches("-?[0-9]+")) Either.right(s.toInt)
   *  else Either.left(new NumberFormatException(s"${s} is not a valid integer."))
   *
   * def reciprocal(i: Int): Either[IllegalArgumentException, Double] =
   *  if (i == 0) Either.left(new IllegalArgumentException("Cannot take reciprocal of 0."))
   *  else Either.right(1.0 / i)
   *
   * def stringify(d: Double): String = d.toString
   *
   * def magic(s: String): Either[Exception, String] =
   *  parse(s).flatMap(reciprocal).map(stringify)
   * }
   * }}}
   *
   * Do these calls return a `Right` value?
   */
  def eitherStyleParse(res0: Boolean, res1: Boolean) = {
    EitherStyle.parse("Not a number").isRight should be(res0)
    EitherStyle.parse("2").isRight should be(res1)
  }

  /**
   * Now, using combinators like `flatMap` and `map`, we can compose our functions together. Will the following incantations return a `Right` value?
   */
  def eitherComposition(res0: Boolean, res1: Boolean, res2: Boolean) = {
    import EitherStyle._

    magic("0").isRight should be(res0)
    magic("1").isRight should be(res1)
    magic("Not a number").isRight should be(res2)
  }

  /**
   * With the composite function that we actually care about, we can pass in strings and then pattern
   * match on the exception. Because `Either` is a sealed type (often referred to as an algebraic data type,
   * or ADT), the compiler will complain if we do not check both the `Left` and `Right` case.
   *
   * In the following exercise we pattern-match on every case the `Either` returned by `magic` can be in.
   * If we leave out any of those clauses the compiler will yell at us, as it should. However,
   * note the `Left(_)` clause - the compiler will complain if we leave that out because it knows
   * that given the type `Either[Exception, String]`, there can be inhabitants of `Left` that are not
   * `NumberFormatException` or `IllegalArgumentException`. However, we "know" by inspection of the source
   * that those will be the only exceptions thrown, so it seems strange to have to account for other exceptions.
   * This implies that there is still room to improve.
   */
  def eitherExceptions(res0: String) = {
    import EitherStyle._

    val result = magic("2") match {
      case Left(_: NumberFormatException)    => "Not a number!"
      case Left(_: IllegalArgumentException) => "Can't take reciprocal of 0!"
      case Left(_)                           => "Unknown error"
      case Right(result)                     => s"Got reciprocal: ${result}"
    }
    result should be(res0)
  }

  /**
   * Instead of using exceptions as our error value, let's instead enumerate explicitly the things that
   * can go wrong in our program.
   *
   * {{{
   * object EitherStyleWithAdts {
   * sealed abstract class Error
   * final case class NotANumber(string: String) extends Error
   * final case object NoZeroReciprocal extends Error
   *
   * def parse(s: String): Either[Error, Int] =
   *  if (s.matches("-?[0-9]+")) Either.right(s.toInt)
   *  else Either.left(NotANumber(s))
   *
   * def reciprocal(i: Int): Either[Error, Double] =
   *  if (i == 0) Either.left(NoZeroReciprocal)
   *  else Either.right(1.0 / i)
   *
   * def stringify(d: Double): String = d.toString
   *
   * def magic(s: String): Either[Error, String] =
   *  parse(s).flatMap(reciprocal).map(stringify)
   * }
   * }}}
   *
   * For our little module, we enumerate any and all errors that can occur. Then, instead of using
   * exception classes as error values, we use one of the enumerated cases. Now when we pattern
   * match, we get much nicer matching. Moreover, since `Error` is `sealed`, no outside code can
   * add additional subtypes which we might fail to handle.
   */
  def eitherErrorsAsAdts(res0: String) = {
    import EitherStyleWithAdts._

    val result = magic("2") match {
      case Left(NotANumber(_))    => "Not a number!"
      case Left(NoZeroReciprocal) => "Can't take reciprocal of 0!"
      case Right(result)          => s"Got reciprocal: ${result}"
    }
    result should be(res0)
  }

  /**
   * = Either in the small, Either in the large =
   *
   * Once you start using `Either` for all your error-handling, you may quickly run into an issue where
   * you need to call into two separate modules which give back separate kinds of errors.
   *
   * {{{
   * sealed abstract class DatabaseError
   * trait DatabaseValue
   *
   * object Database {
   * def databaseThings(): Either[DatabaseError, DatabaseValue] = ???
   * }
   *
   * sealed abstract class ServiceError
   * trait ServiceValue
   *
   * object Service {
   * def serviceThings(v: DatabaseValue): Either[ServiceError, ServiceValue] = ???
   * }
   * }}}
   *
   * Let's say we have an application that wants to do database things, and then take database
   * values and do service things. Glancing at the types, it looks like `flatMap` will do it.
   *
   * {{{
   * def doApp = Database.databaseThings().flatMap(Service.serviceThings)
   * }}}
   *
   * This doesn't work! Well, it does, but it gives us `Either[Object, ServiceValue]` which isn't
   * particularly useful for us. Now if we inspect the `Left`s, we have no clue what it could be.
   * The reason this occurs is because the first type parameter in the two `Either`s are different -
   * `databaseThings()` can give us a `DatabaseError` whereas `serviceThings()` can give us a
   * `ServiceError`: two completely unrelated types. Recall that the type parameters of `Either`
   * are covariant, so when it sees an `Either[E1, A1]` and an `Either[E2, A2]`, it will happily try
   * to unify the `E1` and `E2` in a `flatMap` call - in our case, the closest common supertype is
   * `Object`, leaving us with practically no type information to use in our pattern match.
   *
   * == Solution 1: Application-wide errors ==
   *
   * So clearly in order for us to easily compose `Either` values, the left type parameter must be the same.
   * We may then be tempted to make our entire application share an error data type.
   *
   * {{{
   * sealed abstract class AppError
   * final case object DatabaseError1 extends AppError
   * final case object DatabaseError2 extends AppError
   * final case object ServiceError1 extends AppError
   * final case object ServiceError2 extends AppError
   *
   * trait DatabaseValue
   *
   * object Database {
   * def databaseThings(): Either[AppError, DatabaseValue] = ???
   * }
   *
   * object Service {
   * def serviceThings(v: DatabaseValue): Either[AppError, ServiceValue] = ???
   * }
   *
   * def doApp = Database.databaseThings().flatMap(Service.serviceThings)
   * }}}
   *
   * This certainly works, or at least it compiles. But consider the case where another module wants to just use
   * `Database`, and gets an `Either[AppError, DatabaseValue]` back. Should it want to inspect the errors, it
   * must inspect **all** the `AppError` cases, even though it was only intended for `Database` to use
   * `DatabaseError1` or `DatabaseError2`.
   *
   * == Solution 2: ADTs all the way down ==
   *
   * Instead of lumping all our errors into one big ADT, we can instead keep them local to each module, and have
   * an application-wide error ADT that wraps each error ADT we need.
   *
   * {{{
   * sealed abstract class DatabaseError
   * trait DatabaseValue
   *
   * object Database {
   * def databaseThings(): Either[DatabaseError, DatabaseValue] = ???
   * }
   *
   * sealed abstract class ServiceError
   * trait ServiceValue
   *
   * object Service {
   * def serviceThings(v: DatabaseValue): Either[ServiceError, ServiceValue] = ???
   * }
   *
   * sealed abstract class AppError
   * object AppError {
   * final case class Database(error: DatabaseError) extends AppError
   * final case class Service(error: ServiceError) extends AppError
   * }
   * }}}
   *
   * Now in our outer application, we can wrap/lift each module-specific error into `AppError` and then
   * call our combinators as usual. `Either` provides a convenient method to assist with this, called `Either.leftMap` -
   * it can be thought of as the same as `map`, but for the `Left` side.
   *
   * {{{
   * def doApp: Either[AppError, ServiceValue] =
   * Database.databaseThings().leftMap(AppError.Database).
   * flatMap(dv => Service.serviceThings(dv).leftMap(AppError.Service))
   * }}}
   *
   * Hurrah! Each module only cares about its own errors as it should be, and more composite modules have their
   * own error ADT that encapsulates each constituent module's error ADT. Doing this also allows us to take action
   * on entire classes of errors instead of having to pattern match on each individual one.
   *
   * {{{
   * def awesome =
   * doApp match {
   *  case Left(AppError.Database(_)) => "something in the database went wrong"
   *  case Left(AppError.Service(_))  => "something in the service went wrong"
   *  case Right(_)                   => "everything is alright!"
   * }
   * }}}
   *
   * Let's review the `leftMap` and `map` methods:
   */
  def eitherInTheLarge(
      res0: Either[String, Int],
      res1: Either[String, Int],
      res2: Either[String, Int]
  ) = {
    val right: Either[String, Int] = Right(41)
    right.map(_ + 1) should be(res0)

    val left: Either[String, Int] = Left("Hello")
    left.map(_ + 1) should be(res1)
    left.leftMap(_.reverse) should be(res2)
  }

  /**
   * There will inevitably come a time when your nice `Either` code will have to interact with exception-throwing
   * code. Handling such situations is easy enough.
   *
   * {{{
   * val either: Either[NumberFormatException, Int] =
   * try {
   *  Either.right("abc".toInt)
   * } catch {
   *  case nfe: NumberFormatException => Either.left(nfe)
   * }
   * }}}
   *
   * However, this can get tedious quickly. `Either` provides a `catchOnly` method on its companion object
   * that allows you to pass it a function, along with the type of exception you want to catch, and does the
   * above for you.
   *
   * {{{
   * val either: Either[NumberFormatException, Int] =
   * Either.catchOnly[NumberFormatException]("abc".toInt)
   * }}}
   *
   * If you want to catch all (non-fatal) throwables, you can use `catchNonFatal`.
   */
  def eitherWithExceptions(res0: Boolean, res1: Boolean) = {
    Either.catchOnly[NumberFormatException]("abc".toInt).isRight should be(res0)

    Either.catchNonFatal(1 / 0).isLeft should be(res1)
  }

  /**
   * = Additional syntax =
   *
   * For using Either's syntax on arbitrary data types, you can import `cats.implicits._`. This will
   * make possible to use the `asLeft` and `asRight` methods:
   *
   * {{{
   * import cats.implicits._
   *
   * val right: Either[String, Int] = 7.asRight[String]
   *
   * val left: Either[String, Int] = "hello 🐈s".asLeft[Int]
   * }}}
   *
   * These method promote values to the `Either` data type:
   */
  def eitherSyntax(res0: Either[String, Int]) = {
    import cats.implicits._

    val right: Either[String, Int] = 42.asRight[String]
    right should be(res0)
  }
}
