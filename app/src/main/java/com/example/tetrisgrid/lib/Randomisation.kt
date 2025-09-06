package com.example.tetrisgrid.lib

import kotlin.math.nextUp

object Randomisation {

    /**
     * Produces a piece randomly. Any piece created will be of minimum tile size 2 and will be
     * connected
     *
     * @param gridSize the size of the grid
     */
    fun generateRandomPiece(gridSize: Int): Piece {
        var candidate = listOf<Cell>()
        while (candidate.size < 2) {
            candidate = _generateRandom(gridSize)
        }
        return candidate
    }

    /**
     * Returns a random piece from an existing collection respecting the weight associated to each piece
     *
     * @param weightedPieces a list of `weight -> Piece` pairs, where for the sum of all weights `W_sum`
     * each piece with weight `W` has a probability of being selected: `W / W_sum`
     *
     * For example given the following `weightedPieces`: * `[1 -> P1, 1 -> P2, 2 -> P3, 4 -> , P4]`
     *
     * then we expect half of the time to get a P4, a quarter of the time to get a P3, and the other
     * quarter to get either a P1 or a P2
     */
    fun randomisePiece(weightedPieces: List<Pair<Double, Piece>>): Piece {
        val accumulatedWeights = accumulatedProbabilities(weightedPieces)
        val normalisedWeightedPieces = accumulatedWeights.zip(weightedPieces.map { it.second })

        val random = Math.random()
        return normalisedWeightedPieces
            .firstOrNull { it.first > random }?.second
            ?: normalisedWeightedPieces.last().second // <-- very edge-casey (floating point error + high random number)
    }

    /**
     * Computes the probability that a piece will be selected out of a collection of weighted pieces
     *
     * @param weightedCells a list of `weight -> Piece` pairs, where for the sum of all weights `W_sum`
     * each piece with weight `W` has a probability of being selected: `W / W_sum`
     * @param wanted the wanted piece
     */
    fun getSelectionProbability(weightedCells: List<Pair<Double, Piece>>): Map<Piece, Double> {
        val weightedPiecesNormalised = weightsToProbabilities(weightedCells.map { it.first })

        return weightedPiecesNormalised
            .zip(weightedCells.map { it.second })
            .associate { it.second to it.first }
    }

    private fun _generateRandom(gridSize: Int): Piece {
        val maxCells = gridSize * gridSize - 1
        val nCells = 2 + (Math.random() * maxCells).toInt()
        val taken = mutableMapOf<Int, MutableList<Int>>()
        for (n in 0 until gridSize) {
            taken[n] = mutableListOf()
        }
        for (n in 0 until nCells) {
            val permittedX = taken.entries.filter { it.value.size < gridSize }.map { it.key }
            if (permittedX.isEmpty()) break

            val chosenX = permittedX[(Math.random() * permittedX.size).toInt()]
            val permittedY = (0 until gridSize).toList() - (taken[chosenX] as List<Int>)
            val chosenY = permittedY[(Math.random() * permittedY.size).toInt()]
            taken[chosenX]?.add(chosenY)
        }
        return Transform.getConnected(taken.map { (k, v) -> v.map { k to it } }.flatten()).toList()
    }

    // There's gotta be a better name than this :/
    private fun accumulatedProbabilities(weightedPieces: List<Pair<Double, Piece>>): List<Double> {
        return weightsToProbabilities(weightedPieces.map { it.first })
            .fold(emptyList<Double>()) { acc, d -> acc + listOf(d + (acc.lastOrNull() ?: 0.0)) }
    }

    private fun weightsToProbabilities(weightedPieces: List<Double>): List<Double> {
        // Example: given a list of weights [1,1,2,4] we want to produce a mapping like
        // [1 -> P1, 1 -> P2, 2 -> P3, 4 -> P4] -> [0.125 -> P1, 0.125 -> P2, 0.25 -> P3, 0.5 -> P4]
        val epsilon = (1.0.nextUp() - 1) * 10 // Note: the 10 is a little bit arbitrary to absorb floating point error, but this value is "close to zero"
        val sum = weightedPieces.sumOf { it }
        return when {
            weightedPieces.isEmpty() -> throw IllegalArgumentException("Cannot normalise weights where there are no cells")
            Math.abs(sum) < epsilon -> throw IllegalArgumentException("Sum of weights must be greater than zero")
            sum < 0.0 -> throw IllegalArgumentException("Negative weights do not make sense")
            else -> weightedPieces .map { it / sum }
        }
    }
}