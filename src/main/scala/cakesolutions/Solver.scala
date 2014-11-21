package cakesolutions

import scala.util.Random

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
class Solver(grid: Map[(Int, Int), Boolean]) {
  type Cell = (Int, Int)

  // Current knowledge regarding where our boundaries are
  var left: Option[Int] = None
  var right: Option[Int] = None
  var top: Option[Int] = None
  var bottom: Option[Int] = None

  var zeroCells = Set.empty[Cell]

  var numberOfReads = 0

  // Functions that calculate candidate locations for searching
  def possibleLeftCells(): Set[Cell] = {
    grid.keys.filterNot(zeroCells.contains).filter(cell => left.isEmpty || cell._1 < left.get).toSet
  }

  def possibleRightCells(): Set[Cell] = {
    grid.keys.filterNot(zeroCells.contains).filter(cell => right.isEmpty || cell._1 > right.get).toSet
  }

  def possibleTopCells(): Set[Cell] = {
    grid.keys.filterNot(zeroCells.contains).filter(cell => top.isEmpty || cell._2 < top.get).toSet
  }

  def possibleBottomCells(): Set[Cell] = {
    grid.keys.filterNot(zeroCells.contains).filter(cell => bottom.isEmpty || cell._2 > bottom.get).toSet
  }

  // Randomly select a cell given the current knowledge as to where our boundaries are - we return None when no more cell candidates exist
  def selectCell(): Option[Cell] = {
    Random.shuffle(possibleTopCells() ++ possibleBottomCells() ++ possibleLeftCells() ++ possibleRightCells()).headOption
  }

  def updateKnowledge(cell: Cell): Unit = {
    numberOfReads += 1
    if (grid(cell)) {
      // 1 is present in the cell
      left = left.map(_.min(cell._1)) orElse Some(cell._1)
      right = right.map(_.max(cell._1)) orElse Some(cell._1)
      top = top.map(_.min(cell._2)) orElse Some(cell._2)
      bottom = bottom.map(_.max(cell._2)) orElse Some(cell._2)
    } else {
      // 0 is present in the cell
      zeroCells = zeroCells + cell
    }
  }

  // Main function for calculating the blob boundaries - a None result signifies that no boundaries exist
  def solve(): Option[Result] = {
    var cell = selectCell()

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
