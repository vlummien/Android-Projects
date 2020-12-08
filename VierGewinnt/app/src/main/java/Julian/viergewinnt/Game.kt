package Julian.viergewinnt

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*


class Game : AppCompatActivity() {

    lateinit var player1: Player
    lateinit var player2: Player
    var scoreP1 = 0
    var scoreP2 = 0
    var activePlayer = 2 // 1 = Player1 ; 2 = Player2
    var startingPlayer = 2
    lateinit var board: Array<Array<Pair<Button, Int>>>
    var gameFinished = false
    lateinit var difficulty: String
    lateinit var gameType: String
    var vsCPU = false
    val animation: Animation = AnimationUtils.loadAnimation(MyApplication.appContext, R.anim.blink)
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        gameType = intent.getStringExtra(EXTRA_GAMETYPE)!!
        var opponent = intent.getStringExtra(EXTRA_OPPONENT)

        if ("cpu" == opponent) {
            vsCPU = true
            p2_text.text = "CPU"
            difficulty = intent.getStringExtra(EXTRA_Difficulty)!!
            Toast.makeText(MyApplication.appContext, "You vs CPU", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(MyApplication.appContext, "Player 1 vs Player 2", Toast.LENGTH_SHORT).show()
        }

        initiateBoard()

    }

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

            nextPlayerOnTurn()
        } else {
            Toast.makeText(MyApplication.appContext, "Column is full!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun gameFinished() {
        gameFinished = true
        p1_layout.clearAnimation()
        p2_layout.clearAnimation()
    }

    private fun nextPlayerOnTurn() {
        if (!(gameFinished)) {
            if (activePlayer == 1) {
                activePlayer = 2
                p1_layout.clearAnimation()
                p2_layout.startAnimation(animation)
                if (vsCPU) { // perform cpu turn
                    deactivatePlayerInput() // Player can not perform a turn until cpu has finished its turn
                    handler.postDelayed({
                        performCpuTurn(board)
                    }, 1000)
                }
            } else {
                activePlayer = 1
                p2_layout.clearAnimation()
                p1_layout.startAnimation(animation)
            }
        }
    }

    private fun deactivatePlayerInput() {
        col1_layout.isClickable = false
        col2_layout.isClickable = false
        col3_layout.isClickable = false
        col4_layout.isClickable = false
        col5_layout.isClickable = false
        col6_layout.isClickable = false
        col7_layout.isClickable = false
    }

    private fun activatePlayerInput() {
        col1_layout.isClickable = true
        col2_layout.isClickable = true
        col3_layout.isClickable = true
        col4_layout.isClickable = true
        col5_layout.isClickable = true
        col6_layout.isClickable = true
        col7_layout.isClickable = true
    }

    private fun performCpuTurn(board: Array<Array<Pair<Button, Int>>>) {
        val column = AI.cpuTurn(board, difficulty)
        if (column == -1) {
            performRetardTurn(board)
        } else {
            var colView = getColumn(column)
            turn(colView)
            activatePlayerInput()
        }
    }

    private fun performRetardTurn(board: Array<Array<Pair<Button, Int>>>) {
        var randomColumn = (0..6).random()
        while (!(GameState.isValidMove(board, randomColumn))) {
            randomColumn = (0..6).random()
        }
        var colView = getColumn(randomColumn)
        turn(colView)
        activatePlayerInput()
    }

    private fun activateField(col: Int, row: Int) {
        this.board[col][row] = this.board[col][row].copy(second = activePlayer)
        var button = board[col][row].first

        if (activePlayer == 1) {
            button.setBackgroundResource(R.drawable.red_field)
        } else {
            button.setBackgroundResource(R.drawable.blue_field)
        }
    }

    // Who´se turn?
    private fun initiateBoard() {
        val row1 = arrayOf<Pair<Button, Int>>(
            Pair(field_00, 0),
            Pair(field_01, 0),
            Pair(field_02, 0),
            Pair(field_03, 0),
            Pair(field_04, 0),
            Pair(field_05, 0),
            Pair(field_06, 0)
        )
        val row2 = arrayOf<Pair<Button, Int>>(
            Pair(field_10, 0),
            Pair(field_11, 0),
            Pair(field_12, 0),
            Pair(field_13, 0),
            Pair(field_14, 0),
            Pair(field_15, 0),
            Pair(field_16, 0)
        )
        val row3 = arrayOf<Pair<Button, Int>>(
            Pair(field_20, 0),
            Pair(field_21, 0),
            Pair(field_22, 0),
            Pair(field_23, 0),
            Pair(field_24, 0),
            Pair(field_25, 0),
            Pair(field_26, 0),
        )
        val row4 = arrayOf<Pair<Button, Int>>(
            Pair(field_30, 0),
            Pair(field_31, 0),
            Pair(field_32, 0),
            Pair(field_33, 0),
            Pair(field_34, 0),
            Pair(field_35, 0),
            Pair(field_36, 0),
        )
        val row5 = arrayOf<Pair<Button, Int>>(
            Pair(field_40, 0),
            Pair(field_41, 0),
            Pair(field_42, 0),
            Pair(field_43, 0),
            Pair(field_44, 0),
            Pair(field_45, 0),
            Pair(field_46, 0),
        )
        val row6 = arrayOf<Pair<Button, Int>>(
            Pair(field_50, 0),
            Pair(field_51, 0),
            Pair(field_52, 0),
            Pair(field_53, 0),
            Pair(field_54, 0),
            Pair(field_55, 0),
            Pair(field_56, 0),
        )
        board = arrayOf<Array<Pair<Button, Int>>>(row1, row2, row3, row4, row5, row6)
        for (r in 0..5) {
            for (c in 0..6) {
                board[r][c].first.setBackgroundResource(R.drawable.empty_field)
            }
        }
        if (startingPlayer == 1) {
            activePlayer = 2
            startingPlayer = 2
            if (vsCPU) {
                handler.postDelayed({
                    performRetardTurn(board)
                }, 500)
            }
        } else {
            activePlayer = 1
            startingPlayer = 1
        }
        if (activePlayer == 1) {
            p1_layout.startAnimation(animation)
        } else {
            p2_layout.startAnimation(animation)
        }
    }

    private fun getColumn(column: Int): View {
        var colView = when (column) {
            0 -> col1_layout
            1 -> col2_layout
            2 -> col3_layout
            3 -> col4_layout
            4 -> col5_layout
            5 -> col6_layout
            else -> col7_layout
        }
        return colView
    }
}