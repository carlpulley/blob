package cakesolutions

/**
 * Singleton object holding the Solvers shared mutable state.
 */
object SolverState {

  type Cell = (Int, Int)

  // Current knowledge regarding where our boundaries are
  var left: Option[Int] = None
  var right: Option[Int] = None
  var top: Option[Int] = None
  var bottom: Option[Int] = None

  // Current knowledge of the cells we have looked at
  var oneCells = Set.empty[Cell]
  var zeroCells = Set.empty[Cell]

}
