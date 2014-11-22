package cakesolutions.selection

import cakesolutions.{SolverState, CellSelection}
import scala.util.Random

trait BoundarySelection extends CellSelection {
  this: SolverState =>

  def leftBoundaryCells(): Set[Cell] = {
    grid.keys
      .filter(cell => (cell._1 == left.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .toSet
  }

  def rightBoundaryCells(): Set[Cell] = {
    grid.keys
      .filter(cell => (cell._1 == right.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .toSet
  }

  def topBoundaryCells(): Set[Cell] = {
    grid.keys
      .filter(cell => (cell._2 == top.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .toSet
  }

  def bottomBoundaryCells(): Set[Cell] = {
    grid.keys
      .filter(cell => (cell._2 == bottom.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .toSet
  }

  // Randomly select a boundary cell
  def selectCell(): Option[Cell] = {
    val cells = (leftBoundaryCells() ++ rightBoundaryCells() ++ topBoundaryCells() ++ bottomBoundaryCells())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

}
