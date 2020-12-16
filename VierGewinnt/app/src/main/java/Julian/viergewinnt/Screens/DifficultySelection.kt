package Julian.viergewinnt.Screens

import Julian.viergewinnt.EXTRA_GAMETYPE
import Julian.viergewinnt.MyApplication
import Julian.viergewinnt.R
import Julian.viergewinnt.Screens.Game.Game
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


const val EXTRA_Difficulty = "Julian.viergewinnt.Difficulty"

lateinit var gameType: String

class DifficultySelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_selection)

        gameType = intent.getStringExtra(EXTRA_GAMETYPE)!!
    }

    fun retard(view: View) {
        startGame("retard")
    }

    fun easy(view: View) {
        startGame("easy")
    }

    fun medium(view: View) {
        startGame("medium")
    }

    fun startGame(difficulty: String) {
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, gameType)
            putExtra(EXTRA_Difficulty, difficulty)

        }
        startActivity(intent)
    }
}