package cakesolutions.selection

import cakesolutions.{SolverState, CellSelection}
import scala.util.Random

trait GrowthSelection extends CellSelection {
  this: SolverState =>

  // Functions that calculate candidate locations for searching
  def leftGrowthCells(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._1 == left.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .filter(oneCells.contains)
      .toSet

    oneBoundaryCells
      .map(cell => (cell._1-1, cell._2))
      .filter(_._1 >= 0)
      .toSet
  }

  def rightGrowthCells(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._1 == right.get) && (cell._2 >= top.get) && (cell._2 <= bottom.get))
      .filter(oneCells.contains)
      .toSet

    oneBoundaryCells
      .map(cell => (cell._1+1, cell._2))
      .filter(_._1 < gridWidth)
      .toSet
  }

  def topGrowthCells(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._2 == top.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .filter(oneCells.contains)
      .toSet

    oneBoundaryCells
      .map(cell => (cell._1, cell._2+1))
      .filter(_._2 < gridHeight)
      .toSet
  }

  def bottomGrowthCells(): Set[Cell] = {
    val oneBoundaryCells = grid.keys
      .filter(cell => (cell._2 == bottom.get) && (cell._1 <= right.get) && (cell._1 >= left.get))
      .filter(oneCells.contains)
      .toSet

    oneBoundaryCells
      .map(cell => (cell._1, cell._2-1))
      .filter(_._2 >= 0)
      .toSet
  }

  // Randomly select a growth cell
  def selectCell(): Option[Cell] = {
    val cells = (leftGrowthCells() ++ rightGrowthCells() ++ topGrowthCells() ++ bottomGrowthCells())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

}
