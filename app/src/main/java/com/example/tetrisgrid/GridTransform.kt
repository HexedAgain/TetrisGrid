package com.example.tetrisgrid

class GridTransform() {
    fun rotate(cells: List<Pair<Int, Int>>, axisLength: Int = 4): List<Pair<Int, Int>> {
        return cells.map {
            val halfAxis = (axisLength / 2.0)
            val (newX, newY) = rotate(x = it.second - halfAxis, y = -(it.first - halfAxis))
//            val offsetPair = it.second - halfAxis to it.first - halfAxis
//            val (x, y) = offsetPair
//            val newY = x + halfAxis
//            val newX = -y + halfAxis - 1
            (halfAxis - newY).toInt() to (newX + halfAxis - 1).toInt()
        }
    }
    fun rotate(x: Double, y: Double): Pair<Double, Double> {
        return y to -x
    }
}