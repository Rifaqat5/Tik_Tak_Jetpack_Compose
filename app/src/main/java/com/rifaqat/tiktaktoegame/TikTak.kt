package com.rifaqat.tiktaktoegame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TikTak() {
    val board = remember {
        mutableStateOf(Array(3) { arrayOfNulls<String>(3) })
    }
    val currentPlayer = remember {
        mutableStateOf("X")
    }
    val winner = remember {
        mutableStateOf<String?>(null)
    }
    val initialBoard = Array(3) { arrayOfNulls<String>(3) }
    val initialPlayer = "X"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
//        Title of the game
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Tik Tak Game",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

//        Create Grid for Game
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column {
                for (row in 0..2) {
                    Row {
                        for (col in 0..2) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp),
                                onClick = {
                                    if (board.value[row][col] == null) {
                                        board.value[row][col] = currentPlayer.value
                                        currentPlayer.value =
                                            if (currentPlayer.value == "X") "O" else "X"
                                        winner.value = checkForWinner(board.value)
                                    }
                                }) {
                                Text(
                                    text = board.value[row][col] ?: "",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                Text(
                    text = "Current Player: ${currentPlayer.value}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 20.dp)
                )

                if (winner.value != null) {
                    Text(
                        text = "Winner is ${winner.value}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    LaunchedEffect(true){
                        delay(2000)
                        board.value = initialBoard
                        currentPlayer.value = initialPlayer
                        winner.value = null
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        board.value = initialBoard
                        currentPlayer.value = initialPlayer
                        winner.value = null
                    }) {
                        Text(
                            text = "Reset the game",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.padding(2.dp),
                        )
                    }
                }
            }
        }
    }
}

fun checkForWinner(board: Array<Array<String?>>): String? {
//    Check Winner for Row means 3 boxes in row
    for (row in 0..2) {
        if (board[row][0] != null && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
            return board[row][0]
        }
    }

//    Check Winner for Column means 3 boxes in column
    for (col in 0..2) {
        if (board[0][col] != null && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
            return board[0][col]
        }
    }

//    Check Winner for diagonals means 3 boxes in cross
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        return board[0][0]
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        return board[0][2]
    }

//    if there is no winner simply return null
    return null
}