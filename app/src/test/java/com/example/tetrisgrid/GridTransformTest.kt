package com.example.tetrisgrid

import org.junit.Assert.assertEquals
import org.junit.Test

class GridTransformTest {
    @Test
    fun `given grid is comprised of 4 rows and 4 columns it moves each piece to correct rotated position`() {
        val transform = GridTransform()
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

        val result = transform.rotate(selected)

        assertEquals(expected, result)
    }

    @Test
    fun `given grid is comprised of 3 rows and 3 columns it moves each piece to correct rotated position`() {
        val transform = GridTransform()
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

        val result = transform.rotate(selected, axisLength = 3)

        assertEquals(expected, result)
    }
}