package com.example.tetrisgrid

import com.example.tetrisgrid.lib.Piece
import com.example.tetrisgrid.lib.Randomisation
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class RandomisationTest {
    @Test
    fun `randomisePiece, exploiting law of large numbers, the probability of generating a piece respects weighting`() {
        val pieces = mutableListOf<Piece>()
        val weightedCells = listOf(
            1.0 to listOf(0 to 0),
            1.0 to listOf(0 to 1, 1 to 1),
            2.0 to listOf(0 to 2, 1 to 2, 2 to 2),
            4.0 to listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)
        )

        (0..10_000).forEach {
            pieces.add(Randomisation.randomisePiece(weightedCells))
        }
        val numberOfHitsPerPiece = pieces.groupBy { it.size }.mapValues { it.value.size }

        // Note: these are testing probability; even with a loose margin of error, there is still a
        // very small chance that this test could fail (because, you know ... random!). That said,
        // if they keep failing or the numbers are wildly out then there is a good chance something is broken :)
        with (numberOfHitsPerPiece) {
            assertTrue((get(1) ?: 0) > 1_000 && (get(1) ?: 0) < 1_500) // exact would be 1_250
            assertTrue((get(2) ?: 0) > 1_000 && (get(2) ?: 0) < 1_500) // exact would be 1_250
            assertTrue((get(3) ?: 0) > 2_000 && (get(3) ?: 0) < 3_500) // exact would be 2_500
            assertTrue((get(4) ?: 0) > 4_000 && (get(2) ?: 0) < 6_000) // exact would be 5_000
        }
    }

//    @Test
//    fun `getSelectionProbability, it correctly returns the probability a piece will be selected`() {
//        val weightedCells = listOf(
//            1.0 to listOf(0 to 0),
//            1.0 to listOf(0 to 1, 1 to 1),
//            2.0 to listOf(0 to 2, 1 to 2, 2 to 2),
//            4.0 to listOf(0 to 3, 1 to 3, 2 to 3, 3 to 3)
//        )
//
//        assertEquals(0.125, Randomisation.getSelectionProbability(weightedCells, weightedCells[0].second))
//        assertEquals(0.125, Randomisation.getSelectionProbability(weightedCells, weightedCells[1].second))
//        assertEquals(0.25, Randomisation.getSelectionProbability(weightedCells, weightedCells[2].second))
//        assertEquals(0.5, Randomisation.getSelectionProbability(weightedCells, weightedCells[3].second))
//    }

    @Test
    fun `getSelectionProbability, it correctly returns the probability a piece will be selected`() {
        val weightedCells = listOf(
            1.0 to listOf(0 to 0),
            1.0 to listOf(0 to 1),
            2.0 to listOf(0 to 2),
            4.0 to listOf(0 to 3)
        )

        val probabilities = Randomisation.getSelectionProbability(weightedCells)

        assertEquals(0.125, probabilities[weightedCells[0].second])
        assertEquals(0.125, probabilities[weightedCells[1].second])
        assertEquals(0.25, probabilities[weightedCells[2].second])
        assertEquals(0.5, probabilities[weightedCells[3].second])
    }

    @Test
    fun `getSelectionProbability, if collection is empty it bails out with an error`() {
        val weightedCells = listOf<Pair<Double, Piece>>()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            Randomisation.getSelectionProbability(weightedCells)
        }

        assertEquals("Cannot normalise weights where there are no cells", exception.message)
    }

    @Test
    fun `getSelectionProbability, if weighedSum is effectively zero it bails out with an error`() {
        // Note, the sum of weights here isn't actually 0.0
        val weightedCells = listOf(
            0.1 to listOf(0 to 0),
            0.2 to listOf(0 to 1),
            -0.3 to listOf(0 to 3),
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            Randomisation.getSelectionProbability(weightedCells)
        }

        assertEquals("Sum of weights must be greater than zero", exception.message)
    }

    @Test
    fun `getSelectionProbability, if weighedSum is negative it bails out with an error`() {
        // Note, the sum of weights here isn't actually 0.0
        val weightedCells = listOf(
            -0.1 to listOf(0 to 1),
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            Randomisation.getSelectionProbability(weightedCells)
        }

        assertEquals("Negative weights do not make sense", exception.message)
    }
}