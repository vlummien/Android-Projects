package julian.viergewinnt.screens.game

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import julian.viergewinnt.MyApplication
import julian.viergewinnt.screens.game.CPULogic.AI

class GameViewModel: ViewModel() {

    private var _scoreP1 = MutableLiveData<Int>()
    val scoreP1: LiveData<Int>
        get() = _scoreP1

    private var _scoreP2 = MutableLiveData<Int>()
    val scoreP2: LiveData<Int>
        get() = _scoreP2

    //var activePlayer = 2
    // 1 = Player1 ; 2 = Player2
    private var _activePlayer = MutableLiveData<Int>()
    val activePlayer: LiveData<Int>
        get() = _activePlayer

    private var _startingPlayer = MutableLiveData<Int>()
    val startingPlayer: LiveData<Int>
        get() = _startingPlayer


    private var _board = MutableLiveData<Array<IntArray>>()
    val board: LiveData<Array<IntArray>>
        get() = _board


    private var _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    // Passed Intent Extras
    lateinit var cpuLevel: String
    lateinit var opponent:String
    lateinit var gameType: String

    var vsCPU = false
    var p1Name = "P1"
    var p2Name = "P2" // Blue side player
    private var startingInfoGiven = false
    private val handler = Handler()

    init {
        _scoreP1.value = 0
        _scoreP2.value = 0
        resetBoard()
    }

    fun startingInfo() {
        if (!startingInfoGiven) {
            startingInfoGiven = true
            if (cpuLevel != null && opponent != null) {
                var toastText = ""
                if (opponent == "cpu") {
                    toastText = "Player 1  vs  $cpuLevel CPU"
                    vsCPU = true
                    p2Name = "$cpuLevel CPU"
                } else {
                    toastText = "Player 1  vs  Player 2"
                }
                Toast.makeText(
                    MyApplication.appContext,
                    toastText,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun conductTurn(col: Int) {

        if (gameFinished.value!!) {
            _gameFinished.value = false
            resetBoard()

            if (vsCPU) { // perform cpu turn
                handler.postDelayed({
                    performCpuTurn(board.value!!)
                }, 1000)
            }

            return
        }

        if (GameState.isValidMove(board.value!!, col)) {
            val row = GameState.rowOfDeepestField(board.value!!, col)
            activateField(row, col)

            // Check for WIN
            if (GameState.isWIN(board.value!!, activePlayer.value!!)) {
                var winner = ""
                if (activePlayer.value!! == 1) {
                    _scoreP1.value = scoreP1.value?.plus(1)
                    winner = p1Name
                } else {
                    _scoreP2.value = scoreP2.value?.plus(1)
                    winner = p2Name
                }
                Toast.makeText(
                    MyApplication.appContext,
                    "$winner wins!",
                    Toast.LENGTH_SHORT
                ).show()
                _gameFinished.value = true
            }

            //  Check for DRAW
            if (GameState.isDraw(board.value!!)) {
                Toast.makeText(MyApplication.appContext, "DRAW", Toast.LENGTH_SHORT).show()
                _gameFinished.value = true
            }

            if (!(gameFinished.value!!)){
                nextPlayerOnTurn()
            }
        } else {
            Toast.makeText(MyApplication.appContext, "Column is full!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextPlayerOnTurn() {
        if (activePlayer.value == 1) {
            _activePlayer.value = 2
            if (vsCPU) { // perform cpu turn
                handler.postDelayed({
                    performCpuTurn(board.value!!)
                }, 1000)
            }
        } else {
            _activePlayer.value = 1
        }
    }

    private fun resetBoard() {
        // Create 6x7 Board filled with 0
        _board.value = Array(6){ IntArray(7) {0} }

        if (startingPlayer.value == null) {
            _startingPlayer.value = 2
        }

        // Change starting player
        if (startingPlayer.value == 1) {
            _startingPlayer.value = 2
            _activePlayer.value = 2
        } else {
            _startingPlayer.value = 1
            _activePlayer.value = 1

        }
        _gameFinished.value = false
    }

    private fun activateField(row: Int, col: Int) {
        _board.value!![row][col] = _activePlayer.value!!
        Log.d("activateField", "New Field: col: $col , row: $row")
    }

    private fun performCpuTurn(board: Array<IntArray>) {
        val column = AI.cpuTurn(board, cpuLevel)
        if (column == -1) {
            conductTurn(AI.performRetardTurn(board))
        } else {
            conductTurn(column)
        }
    }
}