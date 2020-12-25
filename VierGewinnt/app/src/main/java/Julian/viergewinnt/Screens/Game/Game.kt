package julian.viergewinnt.screens.game

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import julian.viergewinnt.EXTRA_GAMETYPE
import julian.viergewinnt.EXTRA_OPPONENT
import julian.viergewinnt.MyApplication
import julian.viergewinnt.R
import julian.viergewinnt.databinding.ActivityGameBinding
import julian.viergewinnt.screens.EXTRA_CPU_LEVEL
import kotlinx.android.synthetic.main.activity_game.*



class Game : AppCompatActivity() {

    lateinit var board: Array<Array<Button>>
    val animation: Animation = AnimationUtils.loadAnimation(MyApplication.appContext, R.anim.blink)
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        val gameFinishedObserver = Observer<Boolean> { gameFinished ->
            if (gameFinished) { gameFinished() }
        }
        viewModel.gameFinished.observe(this, gameFinishedObserver)

        val activePlayerAnimationObserver = Observer<Int> { activePlayer ->
            if (activePlayer == 1) {
                activatePlayerInput()
                p1_layout.startAnimation(animation)
                p2_layout.clearAnimation()
            } else {
                if (viewModel.vsCPU) { deactivatePlayerInput() }
                p2_layout.startAnimation(animation)
                p1_layout.clearAnimation()
            }
            updateBoard()
        }
        viewModel.activePlayer.observe(this, activePlayerAnimationObserver)

        viewModel.gameType = intent.getStringExtra(EXTRA_GAMETYPE)!!
        viewModel.opponent = intent.getStringExtra(EXTRA_OPPONENT)!!
        if ("cpu" == viewModel.opponent) {
            viewModel.cpuLevel = intent.getStringExtra(EXTRA_CPU_LEVEL)!!
        } else {
            viewModel.cpuLevel = "no cpu"
        }

        initiateBoard()
        viewModel.startingInfo()

    }

    fun startTurn(view: View) {
        val col = when (view) {
            col1_layout -> 0
            col2_layout -> 1
            col3_layout -> 2
            col4_layout -> 3
            col5_layout -> 4
            col6_layout -> 5
            else -> 6
        }
        viewModel.conductTurn(col)
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

    private fun gameFinished() {
        p1_layout.clearAnimation()
        p2_layout.clearAnimation()
        updateBoard()
        activatePlayerInput() // enables the touch for a restart
    }

    private fun initiateBoard() {
        val row1 = arrayOf<Button>(
            field_00,
            field_01,
            field_02,
            field_03,
            field_04,
            field_05,
            field_06
        )
        val row2 = arrayOf<Button>(
            field_10,
            field_11,
            field_12,
            field_13,
            field_14,
            field_15,
            field_16
        )
        val row3 = arrayOf<Button>(
            field_20,
            field_21,
            field_22,
            field_23,
            field_24,
            field_25,
            field_26
        )
        val row4 = arrayOf<Button>(
            field_30,
            field_31,
            field_32,
            field_33,
            field_34,
            field_35,
            field_36
        )
        val row5 = arrayOf<Button>(
            field_40,
            field_41,
            field_42,
            field_43,
            field_44,
            field_45,
            field_46
        )
        val row6 = arrayOf<Button>(
            field_50,
            field_51,
            field_52,
            field_53,
            field_54,
            field_55,
            field_56
        )
        board = arrayOf<Array<Button>>(row1, row2, row3, row4, row5, row6)
        for (r in 0..5) {
            for (c in 0..6) {
                val color = viewModel.board.value!![r][c]
                paintField(color, r, c)
            }
        }

        // Active Player Animation
        if (viewModel.activePlayer.value == 1) {
            p1_layout.startAnimation(animation)
        } else {
            p2_layout.startAnimation(animation)
        }
    }

    private fun updateBoard() {
        for (row in 0..5) {
            for (col in 0..6) {
                val color = viewModel.board.value!![row][col]
                paintField(color, row, col)
            }
        }
    }

    private fun paintField(color: Int, row: Int, col: Int) {
        val drawable = when (color) {
            0 -> R.drawable.empty_field
            1 -> R.drawable.red_field
            else -> R.drawable.blue_field
        }
        board[row][col].setBackgroundResource(drawable)
    }
}