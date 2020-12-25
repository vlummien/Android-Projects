package julian.viergewinnt.screens.game.CPULogic

import android.widget.Button
import android.widget.Toast
import julian.viergewinnt.MyApplication
import julian.viergewinnt.screens.game.GameState

class AI {
    companion object {

        // detects simple vertical an horizontal rows to score 4 in a row
        fun cpuTurn(board: Array<IntArray>, difficulty: String): Int {

            //score 4th token
            for (c in 0..6) {
                if (GameState.isValidMove(board, c) && GameState.isWIN(
                        placeTokenAt(
                            boardCopy(board),
                            c,
                            2
                        ), 2
                    )
                ) {
                    return c
                }
            }
            // if opponent has 3 -> prevent oppononent to score 4
            for (c in 0..6) {
                if (GameState.isValidMove(board, c) && GameState.isWIN(
                        placeTokenAt(
                            boardCopy(board),
                            c,
                            1
                        ), 1
                    )
                ) {
                    return c
                }
            }

            if (difficulty == "medium") {
                // prevent placing a dumb token so that the opponent just needs to put his 4th token over it
                var losingMoves = mutableListOf<Int>()
                var possibleMoves = mutableListOf<Int>()
                for (c in 0..6) {
                    val boardData = boardCopy(board)
                    if (GameState.isValidMove(board, c)) {
                        val possibleBoard = placeTokenAt(boardData, c, 2)
                            possibleMoves.add(c)
                            if (GameState.isValidMove(possibleBoard, c) &&
                                GameState.isWIN(placeTokenAt(possibleBoard, c, 1), 1)
                            ) {
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

        fun performRetardTurn(board: Array<IntArray>): Int {
            var randomColumn = (0..6).random()
            while (!(GameState.isValidMove(board, randomColumn))) {
                randomColumn = (0..6).random()
            }
            return randomColumn
        }

        private fun boardCopy(board: Array<IntArray>): Array<IntArray> {
            var result = Array(6) { IntArray(7) }
            for (r in 0..5) {
                for (c in 0..6) {
                    result[r][c] = board[r][c]
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

        fun mediumTurn(board: Array<Array<Pair<Button, Int>>>): Int {
            return 0

            // TODO: extend existing vertical rows

            // TODO: extend existing horizontal rows

            // TODO: extend existing diagonal rows
        }
    }
}