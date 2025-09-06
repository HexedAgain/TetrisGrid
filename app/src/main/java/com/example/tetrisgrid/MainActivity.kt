package com.example.tetrisgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tetrisgrid.ui.theme.TetrisGridTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tetrisGrid = TetrisGrid(gridSize = 400.dp, numCells = 3, canModify = true)
        enableEdgeToEdge()
        setContent {
            TetrisGridTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val active by tetrisGrid.active.collectAsState()
                    tetrisGrid.ShowGrid(active)
                }
            }
        }
    }
}

@Preview
@Composable
fun TetrisGrid_TShape_Preview() {
    val tetrisGrid = TetrisGrid(numCells = 3)
    Column(modifier = Modifier.size(200.dp).background(Color.White)) {
        tetrisGrid.ShowGrid(active = listOf(0 to 0, 1 to 0, 2 to 0, 1 to 1))
    }
}