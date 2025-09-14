package com.example.tetrisgrid.lib

import kotlin.math.cos
import kotlin.math.sin

object Maths {
    /**
     * Rotates the cells by the given angle (in degrees). Note, the cells are interpreted to as vectors on a 2D plane.
     *
     * @param cells The cells to rotate
     * @param angle The rotation angle (in degrees)
     *
     * @return the cells rotated
     */
    fun rotate(cells: List<Pair<Double, Double>>, angle: Double): List<Pair<Double, Double>> {
        val radians = angle * (Math.PI / 180.0)
        return cells.map {
            ((it.first * cos(radians)) - (it.second * sin(radians))) to (it.first * sin(radians)) + (it.second * cos(radians))
        }
    }

    /**
     * Projects the lhs onto the rhs
     *
     * @param lhs a vector represented as an x-y coordinate pair
     * @param rhs the vector onto which `lhs` will be projected
     *
     * @return The projection of `lhs` in the direction of `rhs` (i.e. |a|.|b|/|b|^2)
     */
    fun vectorProjection(lhs: Pair<Double, Double>, rhs: Pair<Double, Double>): Double {
        return ((lhs.first * rhs.first) + (lhs.second * rhs.second)) / ((lhs.first * lhs.first) + (rhs.second * rhs.second))
    }
}