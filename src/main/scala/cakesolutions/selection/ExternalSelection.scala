package cakesolutions.selection

import cakesolutions.{SolverState, CellSelection}

import scala.util.Random

trait ExternalSelection extends CellSelection {
  this: SolverState =>

  // Functions that calculate candidate locations for searching
  def possibleLeftCells(): Set[Cell] = {
    grid.keys
      .filterNot(zeroCells.contains)
      .filter(cell => left.isEmpty || cell._1 < left.get)
      .toSet
  }

  def possibleRightCells(): Set[Cell] = {
    grid.keys
      .filterNot(zeroCells.contains)
      .filter(cell => right.isEmpty || cell._1 > right.get)
      .toSet
  }

  def possibleTopCells(): Set[Cell] = {
    grid.keys
      .filterNot(zeroCells.contains)
      .filter(cell => top.isEmpty || cell._2 < top.get)
      .toSet
  }

  def possibleBottomCells(): Set[Cell] = {
    grid.keys
      .filterNot(zeroCells.contains)
      .filter(cell => bottom.isEmpty || cell._2 > bottom.get)
      .toSet
  }

  // Randomly select a cell given the current knowledge as to where our potential boundaries are
  def selectCell(): Option[Cell] = {
    Random.shuffle(possibleTopCells() ++ possibleBottomCells() ++ possibleLeftCells() ++ possibleRightCells()).headOption
  }

}
