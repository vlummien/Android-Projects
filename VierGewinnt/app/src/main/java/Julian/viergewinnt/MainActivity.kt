package julian.viergewinnt

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import julian.viergewinnt.screens.DifficultySelection
import julian.viergewinnt.screens.game.Game

const val EXTRA_GAMETYPE = "Julian.viergewinnt.GAMETYPE"
const val EXTRA_OPPONENT = "Julian.viergewinnt.OPPONENT"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playNormalGame(view: View) {
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, "normal")
            putExtra(EXTRA_OPPONENT, "player")
        }
        startActivity(intent)
    }

    fun playNormalVsComputer(view: View) {
        val intent = Intent(MyApplication.appContext, DifficultySelection::class.java).apply {
            putExtra(EXTRA_GAMETYPE, "normal")
        }
        startActivity(intent)
    }
}