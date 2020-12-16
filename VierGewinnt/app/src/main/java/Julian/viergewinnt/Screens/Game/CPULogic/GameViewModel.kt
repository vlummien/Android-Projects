package Julian.viergewinnt.Screens.Game.CPULogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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


    lateinit var board: Array<Array<Int>>

    var gameFinished = false
    lateinit var difficulty: String
    lateinit var gameType: String
    var vsCPU = false

    init {
        var startingPlayer = 2
    }
}