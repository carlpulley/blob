# Cake Solutions Limited - Carl Pulley

## Blob Example

### Assumptions:

  * it is not necessary to parse a formatted text file into an internal 'blob' representation

### Design Notes

The left, right, top and bottom values define a square that represents the current known boundaries of our blob pattern.
This algorithm operates by growing that square by choosing new cells outside the currently known blob boundaries. When
no more cells may be chosen, then we know the boundaries of our blob.

This implementation will function with arbitrarily large rectangular grids.

Using trait mixins, different choice algorithms may be chosen and so different problems solutions may be contrasted and
compared. Here we define two specific algorithms:
  * basic solution: this algorithm randomly chooses cells external to the square defined by the left, right, top and
  bottom values
  * optimised solution: this algorithm randomly chooses cells in the following preferential order:
      * cells in the external region neighbouring one cells on the boundary
      * cells neighbouring one cells on the boundary
      * any other cell on the boundary.

## Build Instructions

First ensure that Java and [sbt](http://www.scala-sbt.org/download.html) are installed.

To compile:

    sbt compile

To run tests (which includes the supplied example grid):

    sbt test
