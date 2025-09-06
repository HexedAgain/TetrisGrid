package com.example.tetrisgrid.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * TetrisPiece encapsulates the notion of a tetris piece contained within an NxN grid
 *
 * @param gridSize the size of the entire grid containing the piece (measured in Dp)
 * @param numCells the number of rows and columns in this grid: Note - rectangular grids not supported
 * @param canModify pass true if you want to dynamically change the tetris piece
 * @param initialCells the cells with which to seed this TetrisGrid
 */
class TetrisPiece(
    val gridSize: Dp = 200.dp,
    val numCells: Int = 4,
    val canModify: Boolean = false,
    val initialCells: Piece = emptyList()
) {
    /**
     * @param active the cells which make up the tetris piece
     */
    private val _active: MutableStateFlow<Piece> = MutableStateFlow(initialCells)
    val active = _active.asStateFlow()

    /**
     * Renders the tetris piece within its grid
     *
     * @param active a collection of cells (`[row, column]` coordinate pairs) which represent the individual parts of this tetris piece
     */
    @Composable
    fun ShowGrid(
        active: Piece,
    ) {
        Column(modifier = Modifier.size(gridSize)) {
            Box(modifier = Modifier.size(gridSize)) {
                Grid(active)
            }
        }
    }

    @Composable
    private fun Grid(
        active: Piece,
    ) {
        val fractionalSize = gridSize.value / numCells
        (0 until numCells).forEach { row ->
            (0 until numCells).forEach { col ->
                val isActive = active.contains(row to col)
                Box(
                    modifier = Modifier
                        .testTag("$row:$col")
                        .semantics { contentDescription = if (isActive) "$row:$col active" else "$row:$col inactive" }
                        .offset(y = (row * fractionalSize).dp, x = (col * fractionalSize).dp)
                        .border(color = Color.Gray, width = 1.dp)
                        .size((fractionalSize - 1).dp)
                        .then(if (isActive) Modifier.background(Color.Black) else Modifier)
                        .then(if (canModify) Modifier.clickable { toggleCell(row, col) } else Modifier)
                ) { }
            }
        }
    }

    private fun toggleCell(row: Int, col: Int) {
        when {
            !canModify -> {}
            active.value.contains(row to col) -> _active.update { it.filterNot { cell -> cell == row to col } }
            else -> _active.update { it + (row to col) }
        }
    }
}

@Preview
@Composable
fun TetrisGrid_TShape_Preview() {
    val tetrisGrid = TetrisPiece(numCells = 3)
    Column(modifier = Modifier.size(200.dp).background(Color.White)) {
        tetrisGrid.ShowGrid(active = StandardPieces.TShapeThreeByTwo)
    }
}
