package Julian.viergewinnt

import android.widget.Button
import android.widget.Toast

class AI {
    companion object {
        private lateinit var bestTempPairs: MutableList<Pair<Int, Int>>
        // detects simple vertical an horizontal rows to score 4 in a row
        fun cpuTurn(board: Array<Array<Pair<Button, Int>>>, difficulty: String): Int {
            var tokenInARowCounter = 0

            //score 4th token
            for (c in 0..6) {
                val boardData = boardToData(board)
                if (GameState.isValidMove(board, c) && GameState.isWIN(placeTokenAt(boardData, c, 2), 2)) {
                    Toast.makeText(MyApplication.appContext, "Easy", Toast.LENGTH_SHORT).show()
                    return c
                }
            }
            // if opponent has 3 -> prevent oppononent to score 4
            for (c in 0..6) {
                val boardData = boardToData(board)
                if (GameState.isValidMove(board, c) && GameState.isWIN(placeTokenAt(boardData, c, 1), 1)) {
                    Toast.makeText(MyApplication.appContext, "Easy prevented", Toast.LENGTH_SHORT).show()
                    return c
                }
            }

            if (difficulty == "medium") {
                // prevent placing a dumb token so that the opponent just needs to put his 4th token in the row
                var losingMoves = mutableListOf<Int>()
                var possibleMoves = mutableListOf<Int>()
                for (c in 0..6) {
                    val boardData = boardToData(board)
                    if ( GameState.isValidMove(board, c)) {
                        val possibleBoard = placeTokenAt(boardData, c, 2)
                            possibleMoves.add(c)
                            if (GameState.isValidMove(possibleBoard, c) &&
                                GameState.isWIN(placeTokenAt(possibleBoard, c, 1), 1)) {
                                losingMoves.add(c)
                                possibleMoves.remove(c)
                            }
                        }
                }
                if (possibleMoves.size == 0) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "I have no choice",
                        Toast.LENGTH_SHORT
                    ).show()
                    return losingMoves.first()
                } else {
                    return possibleMoves.random()
                }
            }

            return -1
        }

        private fun boardToData(board: Array<Array<Pair<Button, Int>>>): Array<IntArray> {
            var result = Array(6) { IntArray(7) }
            for (r in 0..5) {
                for (c in 0..6) {
                    result[r][c] = board[r][c].second
                }
            }
            return result
        }

        // before call: check if c is a valid move
        private fun placeTokenAt(board: Array<IntArray>, c: Int, player: Int): Array<IntArray> {
            val rowOfDeepestEmptyField = GameState.rowOfDeepestField(board, c)
            board[rowOfDeepestEmptyField][c] = player
            return board
        }

        // For simplifying diagonal row operations - board gets rotated so that diagonal rows are vertical rows
        private fun rotateBoardRight(board: Array<Array<Pair<Button, Int>>>): MutableList<MutableList<Pair<Int, Int>>> {
            var result = mutableListOf<MutableList<Pair<Int, Int>>>()
            for (rD in 0..2) {
                var diagonalRow = mutableListOf<Pair<Int, Int>>()
                for (cD in 0..3) {
                    if (cD == 0 || rD == 0) {
                        diagonalRow.add(Pair(rD, cD))
                    }
                }
                result.add(diagonalRow)
            }
            return result
        }

        private fun isFieldEmpty(pair: Pair<Button, Int>): Boolean {
            return pair.second == 0
        }

        fun mediumTurn(board: Array<Array<Pair<Button, Int>>>): Int {
            return 0

            // TODO: extend existing vertical rows

            // TODO: extend existing horizontal rows

            // TODO: extend existing diagonal rows
        }
    }
}