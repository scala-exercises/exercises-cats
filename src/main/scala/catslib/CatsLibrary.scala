/*
 *  scala-exercises - exercises-cats
 *  Copyright (C) 2015-2020 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package catslib

/** Cats is a library which provides abstractions for functional programming in the Scala programming language.
 *
 * @param name cats
 */
object CatsLibrary extends org.scalaexercises.definitions.Library {
  override def owner      = "scala-exercises"
  override def repository = "exercises-cats"

  override def color = Some("#5B5988")

  override def sections = List(
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
