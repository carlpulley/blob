package cakesolutions

import SolverState._

import scala.util.Random

/**
 * Implementations of this trait control how cells are selected by the solver.
 */
trait CellSelection {

  def grid: Map[Cell, Boolean]

  def gridHeight: Int = {
    val xs = grid.keySet.map(_._2)

    if (xs.isEmpty) {
      0
    } else {
      xs.max
    }
  }

  def gridWidth: Int = {
    val ys = grid.keySet.map(_._1)

    if (ys.isEmpty) {
      0
    } else {
      ys.max
    }
  }

  // Select a cell given the current knowledge as to where our potential boundaries are - we return None when no more cell candidates exist
  def selectCell(): Option[Cell]

  // Random select a cell from our grid, filtered using pred
  def selectAnyCell(pred: Cell => Boolean = (_ => true)): Option[Cell] = {
    Random.shuffle(grid.keys).find(pred)
  }

}
