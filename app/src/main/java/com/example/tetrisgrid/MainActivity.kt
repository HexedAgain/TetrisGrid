package com.example.tetrisgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tetrisgrid.lib.StandardPieces
import com.example.tetrisgrid.lib.TetrisPiece
import com.example.tetrisgrid.ui.theme.TetrisGridTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val grid = TetrisPiece(initialCells = StandardPieces.LShapeReflectedThreeByTwo, numCells = 5, canModify = true)
        enableEdgeToEdge()
        setContent {
            TetrisGridTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    Column (modifier = Modifier.padding(padding)) {
                        val cells by grid.active.collectAsState()
                        grid.ShowGrid(cells)
                        Button(onClick = { grid.rotate()} ) {
                            Text(text = "Rotate CW")
                        }
                    }
                }
            }
        }
    }
}