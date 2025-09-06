package com.example.tetrisgrid.lib

object Transform {
    /**
     * Rotates the cells about a `square` grid with the given axis length
     *
     * @param cells A collection of coordinate-pairs indicating active grid cells, ordered `[row, column]` (i.e. `[y, x]`)
     * @param rotateClockwise If true, rotation will be 90 degrees clockwise, otherwise it is 90 degrees counter-clockwise
     * @param axisLength the number of cells on either x or y axis
     *
     * @return a collection of cells with rotation applied to each element
     */
    fun rotate(cells: Piece, rotateClockwise: Boolean = true, axisLength: Int = 4): Piece {
        return cells.map {
            val halfAxis = (axisLength / 2.0)
            val (shiftX, shiftY) = it.second - halfAxis to halfAxis - it.first
            if (rotateClockwise) {
                val (newX, newY) = rotate90CW(shiftX, shiftY)
                (halfAxis - newY).toInt() to (newX + halfAxis - 1).toInt()
            } else {
                val (newX, newY) = rotate90CCW(shiftX, shiftY)
                (halfAxis - newY - 1).toInt() to (newX + halfAxis).toInt()
            }
        }
    }

    /**
     * Translates the given cells, note: negative translation is not restricted
     *
     * @param cells A collection of coordinate-pairs indicating active grid cells, ordered `[row, column]` (i.e. `[y, x]`)
     * @param direction The direction to move
     * @param distance The number of cells by which to move each cell
     *
     * @return a collection of cells with translation applied to each element
     */
    fun translate(cells: Piece, direction: Direction, distance: Int = 1): Piece {
        return cells.map { it.first + (direction.translateY * distance) to it.second + (direction.translateX * distance)}
    }

    /**
     * Compares the given collections of cells. If through a sequence of translations or rotations
     * one may be transformed into the other it returns true, otherwise it returns false
     *
     * @param lhs a collection of cells representing the left-hand-piece
     * @param rhs a collection of cells representing the right-hand-piece - to compare against
     *
     * @return whether the cells are structurally equivalent
     */
    fun cellsAreEquivalent(lhs: Piece, rhs: Piece): Boolean {
        val lhsTopLeft = translateToTopLeft(lhs)
        var rotatedCells = rhs
        (0 until 4).forEach { _ ->
            if (translateToTopLeft(rotatedCells) == lhsTopLeft) {
                return true
            }
            rotatedCells = rotate(rotatedCells)
        }
        return false
    }

    /**
     * returns whether the active cells are connected horizontally or vertically (i.e. you can draw them without lifting pen off table)
     *
     * @param piece a collection of cells representing the piece
     */
    fun isConnected(piece: Piece): Boolean {
        return getConnected(piece) == piece.toSet()
    }

    enum class Direction(val translateX: Int, val translateY: Int) {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1)
    }

    private fun translateToTopLeft(cells: Piece): Piece {
        val leftDistance = cells.minBy { it.second }.second.takeIf { it >= 0 } ?: 0
        val topDistance = cells.minBy { it.first }.first.takeIf { it >= 0 } ?: 0
        return translate(translate(cells, Direction.LEFT, leftDistance), Direction.UP, topDistance)
    }

    private fun rotate90CW(x: Double, y: Double): Pair<Double, Double> {
        return y to -x
    }

    private fun rotate90CCW(x: Double, y: Double): Pair<Double, Double> {
        return -y to x
    }

    internal fun getConnected(cells: Piece): Set<Pair<Int, Int>> {
        val connectedCells = mutableSetOf<Cell>(cells.first())
        cells.forEach { cell ->
            if (!connectedCells.contains(cell)) {
                for (direction in Direction.entries) {
                    val translated = translate(listOf(cell), direction).first()
                    if (connectedCells.contains(translated)) {
                        connectedCells.add(cell)
                        break
                    }
                }
            }
        }
        return connectedCells
    }
}