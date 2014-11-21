package cakesolutions

import org.specs2.mutable.Specification

class SolverSpec extends Specification {

  "Solver" should {
    "solve supplied example" in {
      val grid = Map(
        (0, 0) -> false, (1, 0) -> false, (2, 0) -> false, (3, 0) -> false, (4, 0) -> false, (5, 0) -> false, (6, 0) -> false, (7, 0) -> false, (8, 0) -> false, (9, 0) -> false,
        (0, 1) -> false, (1, 1) -> false, (2, 1) -> true,  (3, 1) -> true,  (4, 1) -> true,  (5, 1) -> false, (6, 1) -> false, (7, 1) -> false, (8, 1) -> false, (9, 1) -> false,
        (0, 2) -> false, (1, 2) -> false, (2, 2) -> true,  (3, 2) -> true,  (4, 2) -> true,  (5, 2) -> true,  (6, 2) -> true,  (7, 2) -> false, (8, 2) -> false, (9, 2) -> false,
        (0, 3) -> false, (1, 3) -> false, (2, 3) -> true,  (3, 3) -> false, (4, 3) -> false, (5, 3) -> false, (6, 3) -> true,  (7, 3) -> false, (8, 3) -> false, (9, 3) -> false,
        (0, 4) -> false, (1, 4) -> false, (2, 4) -> true,  (3, 4) -> true,  (4, 4) -> true,  (5, 4) -> true,  (6, 4) -> true,  (7, 4) -> false, (8, 4) -> false, (9, 4) -> false,
        (0, 5) -> false, (1, 5) -> false, (2, 5) -> false, (3, 5) -> false, (4, 5) -> true,  (5, 5) -> false, (6, 5) -> true,  (7, 5) -> false, (8, 5) -> false, (9, 5) -> false,
        (0, 6) -> false, (1, 6) -> false, (2, 6) -> false, (3, 6) -> false, (4, 6) -> true,  (5, 6) -> false, (6, 6) -> true,  (7, 6) -> false, (8, 6) -> false, (9, 6) -> false,
        (0, 7) -> false, (1, 7) -> false, (2, 7) -> false, (3, 7) -> false, (4, 7) -> true,  (5, 7) -> true,  (6, 7) -> true,  (7, 7) -> false, (8, 7) -> false, (9, 7) -> false,
        (0, 8) -> false, (1, 8) -> false, (2, 8) -> false, (3, 8) -> false, (4, 8) -> false, (5, 8) -> false, (6, 8) -> false, (7, 8) -> false, (8, 8) -> false, (9, 8) -> false,
        (0, 9) -> false, (1, 9) -> false, (2, 9) -> false, (3, 9) -> false, (4, 9) -> false, (5, 9) -> false, (6, 9) -> false, (7, 9) -> false, (8, 9) -> false, (9, 9) -> false
      )

      val solver = new Solver(grid)

      solver.solve() must beSome.like {
        case result =>
          println(s"DEBUG: number of reads = ${result.reads}\n")
          result.left === 2
          result.right === 6
          result.top === 1
          result.bottom === 7
      }
    }
  }

}
