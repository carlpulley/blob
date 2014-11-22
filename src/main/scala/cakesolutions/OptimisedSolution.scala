package cakesolutions

import scala.util.Random
import SolverState._

/**
 * Cells are selectively chosen using the boundary of the square defined by left, right, top and bottom.
 *
 * This code defines three types of cells:
 *   - growth cells:         these are (unexplored) cells that 'might' be connected to known one cells on our boundary
 *   - (boundary) one cells: these are (unexplored) cells on the boundary that are neighbours to known one cells
 *   - boundary cells:       these are any other (unexplored) cells on the boundary.
 *
 * When none of these cells can be found, then we have defined the maximal shape for our blob.
 *
 * This solution assumes that the blob is connected and so it is only necessary to consider potential points at which
 * the blob may cross the currently defined boundary of the square defined by left, right, top and bottom.
 */
trait OptimisedSolution extends CellSelection {

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

  // Randomly select a growth cell
  def selectGrowthCell(): Option[Cell] = {
    val cells = (leftGrowthCells() ++ rightGrowthCells() ++ topGrowthCells() ++ bottomGrowthCells())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

  // Randomly select a 1 cell from the currently known blob boundary
  def selectOneCell(): Option[Cell] = {
    val cells = (leftOneCell() ++ rightOneCell() ++ topOneCell() ++ bottomOneCell())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

  // Randomly select a boundary cell
  def selectBoundaryCell(): Option[Cell] = {
    val cells = (leftBoundaryCells() ++ rightBoundaryCells() ++ topBoundaryCells() ++ bottomBoundaryCells())
      .filterNot(zeroCells.contains)
      .filterNot(oneCells.contains)

    Random.shuffle(cells).headOption
  }

  // Preferentially select (in the following order): a growth cell; or boundary 1 cell; or any other boundary cell
  def selectCell(): Option[Cell] = {
    if (oneCells.isEmpty) {
      // We have not yet located any one cells, so keep choosing at random from unexplored cells
      selectAnyCell(cell => zeroCells.isEmpty || !zeroCells.contains(cell))
    } else {
      // At least one blob member located
      selectGrowthCell() orElse selectOneCell() orElse selectBoundaryCell()
    }
  }

}
