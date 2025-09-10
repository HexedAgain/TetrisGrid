package com.example.tetrisgrid

import com.example.tetrisgrid.lib.Transform
import com.example.tetrisgrid.lib.Transform.Direction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TransformTest {
    @Test
    fun `rotate, CW - given grid is comprised of 4 rows and 4 columns it maps each cell to correct rotated position`() {
        val selected = listOf(
            0 to 0, 0 to 1, 0 to 2, 0 to 3,
            1 to 0, 1 to 1, 1 to 2, 1 to 3,
            2 to 0, 2 to 1, 2 to 2, 2 to 3,
            3 to 0, 3 to 1, 3 to 2, 3 to 3,
        )
        val expected = listOf(
            0 to 3, 1 to 3, 2 to 3, 3 to 3,
            0 to 2, 1 to 2, 2 to 2, 3 to 2,
            0 to 1, 1 to 1, 2 to 1, 3 to 1,
            0 to 0, 1 to 0, 2 to 0, 3 to 0,
        )

        val result = Transform.rotate(piece = selected, axisLength = 4)

        assertEquals(expected, result)
    }

    @Test
    fun `rotate, CW - given grid is comprised of 3 rows and 3 columns it maps each cell to correct rotated position`() {
        val selected = listOf(
            0 to 0, 0 to 1, 0 to 2,
            1 to 0, 1 to 1, 1 to 2,
            2 to 0, 2 to 1, 2 to 2,
        )
        val expected = listOf(
            0 to 2, 1 to 2, 2 to 2,
            0 to 1, 1 to 1, 2 to 1,
            0 to 0, 1 to 0, 2 to 0,
        )

        val result = Transform.rotate(piece = selected, axisLength = 3)

        assertEquals(expected, result)
    }


    @Test
    fun `rotate, CCW - given grid is comprised of 3 rows and 3 columns it maps each cell to correct rotated position`() {
        val selected = listOf(
            0 to 0, 0 to 1, 0 to 2,
            1 to 0, 1 to 1, 1 to 2,
            2 to 0, 2 to 1, 2 to 2,
        )
        val expected = listOf(
            2 to 0, 1 to 0, 0 to 0,
            2 to 1, 1 to 1, 0 to 1,
            2 to 2, 1 to 2, 0 to 2,
        )

        val result = Transform.rotate(piece = selected, axisLength = 3, rotateClockwise = false)

        assertEquals(expected, result)
    }

    @Test
    fun `translate left distance 1 - it moves cells one unit left`() {
        val selected = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)
        val expected = listOf(0 to -1, 0 to 0, 1 to -1, 1 to 0)

        val result = Transform.translate(piece = selected, direction = Direction.LEFT)

        assertEquals(expected, result)
    }

    @Test
    fun `translate right distance 2 - it moves cells two units right`() {
        val selected = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)
        val expected = listOf(0 to 2, 0 to 3, 1 to 2, 1 to 3)

        val result = Transform.translate(piece = selected, direction = Direction.RIGHT, distance = 2)

        assertEquals(expected, result)
    }

    @Test
    fun `translate up distance 1 - it moves cells one unit up`() {
        val selected = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)
        val expected = listOf(-1 to 0, -1 to 1, 0 to 0, 0 to 1)

        val result = Transform.translate(piece = selected, direction = Direction.UP)

        assertEquals(expected, result)
    }

    @Test
    fun `translate down distance 1 - it moves cells two units down`() {
        val selected = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)
        val expected = listOf(2 to 0, 2 to 1, 3 to 0, 3 to 1)

        val result = Transform.translate(piece = selected, direction = Direction.DOWN, distance = 2)

        assertEquals(expected, result)
    }

    @Test
    fun `cellsAreEquivalent, if two pieces are the same it returns true`() {
        val lhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)
        val rhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if one piece is a horizontal translation of the other it returns true`() {
        val lhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)
        val rhs = listOf(0 to 1, 0 to 2, 0 to 3, 0 to 4)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if one piece is a vertical translation of the other it returns true`() {
        val lhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)
        val rhs = listOf(1 to 0, 1 to 1, 1 to 2, 1 to 3)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if one piece is a clockwise rotation of the other it returns true`() {
        val lhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)
        val rhs = listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if one piece is a counter-clockwise rotation of the other it returns true`() {
        val lhs = listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)
        val rhs = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if one piece is a rotation and translation of the other it returns true`() {
        val lhs = listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)
        val rhs = listOf(3 to 0, 3 to 1, 3 to 2, 3 to 3)

        assertTrue(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `cellsAreEquivalent, if the pieces are different it returns false`() {
        val lhs = listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)
        val rhs = listOf(2 to 0, 2 to 1, 2 to 2, 2 to 0)

        assertFalse(Transform.cellsAreEquivalent(lhs, rhs))
    }

    @Test
    fun `isConnected, for a 4x1 line it returns true`() {
        val cells = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)

        assertTrue(Transform.isConnected(cells))
    }

    @Test
    fun `isConnected, for a 4x1 line with cell removed it returns false`() {
        val cells = listOf(0 to 0, 0 to 1, 0 to 3)

        assertFalse(Transform.isConnected(cells))
    }

    @Test
    fun `isConnected, for a 3x2 T-shape it returns true`() {
        val cells = listOf(0 to 0, 0 to 1, 0 to 2, 1 to 1)

        assertTrue(Transform.isConnected(cells))
    }

    @Test
    fun `isConnected, for a 3x2 T-shape with top-middle removed it returns false`() {
        val cells = listOf(0 to 0, 0 to 2, 1 to 1)

        assertFalse(Transform.isConnected(cells))
    }
}