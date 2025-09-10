package com.example.tetrisgrid.lib

object Transform {
    /**
     * Rotates the cells about a `square` grid with the given axis length
     *
     * @param piece A collection of coordinate-pairs indicating active grid cells, ordered `[row, column]` (i.e. `[y, x]`)
     * @param rotateClockwise If true, rotation will be 90 degrees clockwise, otherwise it is 90 degrees counter-clockwise
     * @param axisLength the number of cells on either x or y axis
     *
     * @return a collection of cells with rotation applied to each element
     */
    fun rotate(piece: Piece, axisLength: Int, rotateClockwise: Boolean = true): Piece {
        return piece.map {
            if (rotateClockwise) {
                it.second to (axisLength - it.first - 1).toInt()
            } else {
                (axisLength - it.second - 1).toInt() to it.first
            }
        }
    }

    /**
     * Translates the given cells, note: negative translation is not restricted
     *
     * @param piece A collection of coordinate-pairs indicating active grid cells, ordered `[row, column]` (i.e. `[y, x]`)
     * @param direction The direction to move
     * @param distance The number of cells by which to move each cell
     *
     * @return a collection of cells with translation applied to each element
     */
    fun translate(piece: Piece, direction: Direction, distance: Int = 1): Piece {
        return piece.map { it.first + (direction.translateY * distance) to it.second + (direction.translateX * distance)}
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
            // Note: to avoid measuring pieces, take a "large" axis length for rotation
            rotatedCells = rotate(rotatedCells, axisLength = Int.MAX_VALUE / 2)
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