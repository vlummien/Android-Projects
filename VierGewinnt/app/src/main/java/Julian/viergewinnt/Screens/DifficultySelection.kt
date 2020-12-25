package julian.viergewinnt.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import julian.viergewinnt.EXTRA_GAMETYPE
import julian.viergewinnt.EXTRA_OPPONENT
import julian.viergewinnt.MyApplication
import julian.viergewinnt.R
import julian.viergewinnt.screens.game.Game


const val EXTRA_CPU_LEVEL = "Julian.viergewinnt.Difficulty"

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

    private fun startGame(difficulty: String) {
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, gameType)
            putExtra(EXTRA_CPU_LEVEL, difficulty)
            putExtra(EXTRA_OPPONENT, "cpu")

        }
        startActivity(intent)
    }
}