package cakesolutions

/**
 * Case class holding solver result.
 *
 * @param left   Left co-ordinate of the blob
 * @param right  Right co-ordinate of the blob
 * @param top    Top co-ordinate of the blob
 * @param bottom Bottom co-ordinate of the blob
 * @param reads  Number of reads of the grid
 */
case class Result(left: Int, right: Int, top: Int, bottom: Int, reads: Int)

/**
 * Class for calculating the boundaries of a blob.
 *
 * Grid is here represented as a
 */
abstract class Solver(val grid: Map[(Int, Int), Boolean]) extends SolverState {
  this: CellSelection =>

  var numberOfReads = 0

  def updateKnowledge(cell: Cell): Unit = {
    numberOfReads += 1
    if (grid(cell)) {
      // 1 is present in the cell
      left     = left.map(_.min(cell._1)) orElse Some(cell._1)
      right    = right.map(_.max(cell._1)) orElse Some(cell._1)
      top      = top.map(_.min(cell._2)) orElse Some(cell._2)
      bottom   = bottom.map(_.max(cell._2)) orElse Some(cell._2)
      oneCells = oneCells + cell
    } else {
      // 0 is present in the cell
      zeroCells = zeroCells + cell
    }
  }

  def reset(): Unit = {
    left = None
    right = None
    top = None
    bottom = None
    oneCells = Set.empty[Cell]
    zeroCells = Set.empty[Cell]
  }

  // Main function for calculating the blob boundaries - a None result signifies that no boundaries exist
  def solve(): Option[Result] = {
    // Start with a random cell in our blob
    var cell = selectAnyCell()

    while (cell.nonEmpty) {
      updateKnowledge(cell.get)
      cell = selectCell()
    }

    for {
      t <- top
      l <- left
      b <- bottom
      r <- right
    } yield Result(l, r, t, b, numberOfReads)
  }

}
