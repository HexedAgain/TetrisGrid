package com.example.tetrisgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TetrisGrid(
    val gridSize: Dp = 200.dp,
    val numCells: Int = 4,
    val canModify: Boolean = false
) {

    private val _active: MutableStateFlow<List<Pair<Int, Int>>> = MutableStateFlow(listOf())
    val active = _active.asStateFlow()

    @Composable
    fun ShowGrid(
        active: List<Pair<Int, Int>>,
    ) {
        Column(modifier = Modifier.size(gridSize)) {
            Box(modifier = Modifier.size(gridSize)) {
                Mesh()
                Grid(active) { (row, col) ->
                    when {
                        !canModify -> {}
                        active.contains(row to col) -> _active.update { it.filterNot { cell -> cell == row to col } }
                        else -> _active.update { it + (row to col) }
                    }
                }
            }
        }
    }

    @Composable
    private fun Mesh() {
        (0..numCells).forEach { offset ->
            HorizontalDivider(
                color = Color.LightGray,
                modifier = Modifier.offset(y = (offset * (gridSize.value / numCells)).dp)
            )
            VerticalDivider(
                color = Color.LightGray,
                modifier = Modifier.offset(x = (offset * (gridSize.value / numCells)).dp)
            )
        }
    }

    @Composable
    private fun Grid(
        active: List<Pair<Int, Int>>,
        onClick: (Pair<Int, Int>) -> Unit
    ) {
        val fractionalSize = gridSize.value / numCells
        (0 until numCells).forEach { row ->
            (0 until numCells).forEach { col ->
                val isActive = active.contains(row to col)
                Box(
                    modifier = Modifier
                        .offset(x = (row * fractionalSize).dp, y = (col * fractionalSize).dp)
                        .size(fractionalSize.dp)
                        .then(if (isActive) Modifier.background(Color.Blue.copy(alpha = .25f)) else Modifier)
                        .then(if (canModify) Modifier.clickable { onClick(row to col) } else Modifier)
                ) { }
            }
        }
    }
}