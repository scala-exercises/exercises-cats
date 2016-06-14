package catslib

/** Cats is an experimental library intended to provide abstractions for functional programming in Scala.
  *
  * @param name cats
  */
object CatsLibrary extends exercise.Library {
  override def owner = "scala-exercises"
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
    XorSection,
    ValidatedSection
  )
}
