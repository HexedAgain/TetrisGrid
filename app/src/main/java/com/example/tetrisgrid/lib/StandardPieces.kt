package com.example.tetrisgrid.lib

object StandardPieces {
    /*
     * [][][][]
     */
    val LineShapeFourByOne = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)

    /*
     * [][]
     * [][]
     */
    val SquareShapeTwoByTwo = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)

    /*
     * [][][]
     *   []
     */
    val TShapeThreeByTwo = listOf(0 to 0, 0 to 1, 0 to 2, 1 to 1)

    /*
     * [][][]
     * []
     */
    val LShapeThreeByTwo = listOf(0 to 0, 0 to 1, 0 to 2, 1 to 0)

    /*
     * [][][]
     *     []
     */
    val LShapeReflectedThreeByTwo = listOf(0 to 0, 0 to 1, 0 to 2, 1 to 2)

    /*
     *   [][]
     * [][]
     */
    val SnakeShapeThreeByTwo = listOf(0 to 1, 0 to 2, 1 to 0, 1 to 1)

    /*
     * [][]
     *   [][]
     */
    val SnakeShapeReflectedThreeByTwo = listOf(0 to 0, 0 to 1, 1 to 1, 1 to 2)
}