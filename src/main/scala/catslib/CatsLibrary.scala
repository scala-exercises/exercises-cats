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

/**
 * Cats is a library which provides abstractions for functional programming in the Scala programming language.
 *
 * @param name cats
 */
object CatsLibrary extends org.scalaexercises.definitions.Library {
  override def owner      = "scala-exercises"
  override def repository = "exercises-cats"

  override def color = Some("#5B5988")

  override def sections =
    List(
      SemigroupSection,
      MonoidSection,
      FunctorSection,
      ApplySection,
      ApplicativeSection,
      MonadSection,
      FoldableSection,
      TraverseSection,
      IdentitySection,
      EitherSection,
      ValidatedSection,
      EvalSection
    )

  override def logoPath = "cats"
}
