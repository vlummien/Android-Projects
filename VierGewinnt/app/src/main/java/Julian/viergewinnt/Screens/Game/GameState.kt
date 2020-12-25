package julian.viergewinnt.screens.game

class GameState {

    companion object {

        fun isValidMove(board: Array<IntArray>, col: Int): Boolean { // checks if there is an empty field
            for (x in 0 .. 5) {
                if (board[x][col] == 0) {
                    return true
                }
            }
            return false
        }

        fun rowOfDeepestField(board: Array<IntArray>, col: Int): Int { // returns deepest field that is empty
            for (x in 0 .. 5) {
                if (board[x][col] == 0) {
                    return x
                }
            }
            return -1
        }

        fun isDraw(board: Array<IntArray>): Boolean {
            for (r in 0..5) {
                for (c in 0..6) {
                    if (board[r][c] == 0) {
                        return false
                    }
                }
            }
            return true
        }

        fun isWIN(board: Array<IntArray>, activePlayer: Int): Boolean {
            var winCounter = 0
            // Check for rows
            for (r in 0..5) {
                for (c in 0..6) {
                    if (board[r][c] == activePlayer) {
                        winCounter++
                    } else {
                        winCounter = 0
                    }
                    if (winCounter == 4) {
                        return true
                    }
                }
                winCounter = 0
            }

            // Check for Columns
            for (c in 0..6) {
                for (r in 0..5) {
                    if (board[r][c] == activePlayer) {
                        winCounter++
                    }else {
                        winCounter = 0
                    }
                    if (winCounter == 4) {
                        return true
                    }
                }
                winCounter = 0
            }

            // Check Diagonal

            // Diagonal bottom left to top right
            var diagonalQueues = mutableListOf<MutableList<Int>>()
            // generates all possible diagonal lists
            for (rD in 0..2) {
                for (cD in 0..3) {
                    if (cD == 0 || rD == 0) {

                        var tmpQueue = mutableListOf<Int>()
                        for (c in cD..6) {
                            for (r in rD..5) {
                                if (c - r == cD - rD) {
                                    tmpQueue.add(board[r][c])
                                }
                            }
                        }
                        diagonalQueues.add(tmpQueue)
                    }

                }
            }
            for (l in diagonalQueues.indices) {
                for (x in diagonalQueues[l].indices) {
                    if (diagonalQueues[l][x] == activePlayer) {
                        winCounter++
                    } else {
                        winCounter = 0
                    }
                    if (winCounter == 4) {
                        return true
                    }
                }
                winCounter = 0
            }

            // Diagonal bottom right to top left
            diagonalQueues.clear()
            // generates all possible diagonal lists
            for (rD in 0..2) {
                for (cD in 3..6) {
                    if (cD == 6 || rD == 0) {

                        var tmpQueue = mutableListOf<Int>()
                        for (c in 0..6) {
                            for (r in 0..5) {
                                if (c + r == cD + rD) {
                                    tmpQueue.add(board[r][c])
                                }
                            }
                        }
                        diagonalQueues.add(tmpQueue)
                    }

                }
            }
            for (l in diagonalQueues.indices) {
                for (x in diagonalQueues[l].indices) {
                    if (diagonalQueues[l][x] == activePlayer) {
                        winCounter++
                    } else {
                        winCounter = 0
                    }
                    if (winCounter == 4) {
                        return true
                    }
                }
                winCounter = 0
            }

            return false
        }
    }
}
