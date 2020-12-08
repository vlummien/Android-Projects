    fun turn(view: View) {
        val col = when (view) {
            col1_layout -> 0
            col2_layout -> 1
            col3_layout -> 2
            col4_layout -> 3
            col5_layout -> 4
            col6_layout -> 5
            else -> 6
        }

        if (gameFinished) {
            gameFinished = false
            initiateBoard()
            return
        }

        if (GameState.isValidMove(board, col)) {
            val row = GameState.rowOfDeepestField(board, col)
            activateField(row, col)

            // Check for WIN
            if (GameState.isWIN(board, activePlayer)) {
                if (activePlayer == 1) {
                    scoreP1++
                    p1_score_text.setText(scoreP1.toString())
                } else {
                    scoreP2++
                    p2_score_text.setText(scoreP2.toString())
                }
                Toast.makeText(
                    MyApplication.appContext,
                    "Player " + activePlayer.toString() + "wins!",
                    Toast.LENGTH_SHORT
                ).show()
                gameFinished()
            }

            //  Check for DRAW
            if (GameState.isDraw(board)) {
                Toast.makeText(MyApplication.appContext, "DRAW", Toast.LENGTH_SHORT).show()
                gameFinished()
            }

            endTurn()
        } else {
            Toast.makeText(MyApplication.appContext, "Column is full!", Toast.LENGTH_SHORT).show()
        }
    }