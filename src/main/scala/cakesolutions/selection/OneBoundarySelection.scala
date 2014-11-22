package cakesolutions.selection

import cakesolutions.{SolverState, CellSelection}
import scala.util.Random

trait OneBoundarySelection extends CellSelection {
  this: SolverState =>

  def leftOneCell(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._1 == left.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .filter(oneCells.contains)
      .toSet

    val topShift = oneBoundaryCells.map(cell => (cell._1, cell._2-1))

    val bottomShift = oneBoundaryCells.map(cell => (cell._1, cell._2+1))

    (topShift ++ bottomShift)
      .filter(_._2 >= 0)
      .filter(_._2 < gridWidth)
      .toSet
  }

  def rightOneCell(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._1 == right.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .filter(oneCells.contains)
      .toSet

    val topShift = oneBoundaryCells.map(cell => (cell._1, cell._2-1))

    val bottomShift = oneBoundaryCells.map(cell => (cell._1, cell._2+1))

    (topShift ++ bottomShift)
      .filter(_._2 >= 0)
      .filter(_._2 < gridWidth)
      .toSet
  }

  def topOneCell(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._2 == top.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .filter(oneCells.contains)
      .toSet

    val leftShift = oneBoundaryCells.map(cell => (cell._1-1, cell._2))

    val rightShift = oneBoundaryCells.map(cell => (cell._1+1, cell._2))

    (leftShift ++ rightShift)
      .filter(_._1 >= 0)
      .filter(_._1 < gridHeight)
      .toSet
  }

  def bottomOneCell(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._2 == bottom.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .filter(oneCells.contains)
      .toSet

    val leftShift = oneBoundaryCells.map(cell => (cell._1-1, cell._2))

    val rightShift = oneBoundaryCells.map(cell => (cell._1+1, cell._2))

    (leftShift ++ rightShift)
      .filter(_._1 >= 0)
      .filter(_._1 < gridHeight)
      .toSet
  }

  // Randomly select a 1 cell from the currently known blob boundary
  def selectCell(): Option[Cell] = {
    val cells = (leftOneCell() ++ rightOneCell() ++ topOneCell() ++ bottomOneCell())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

}
