package Julian.viergewinnt

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
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, gameType)
            putExtra(EXTRA_OPPONENT, "cpu")
            putExtra(EXTRA_Difficulty, "retard")

        }
        startActivity(intent)
    }

    fun easy(view: View) {
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, gameType)
            putExtra(EXTRA_OPPONENT, "cpu")
            putExtra(EXTRA_Difficulty, "easy")
        }
        startActivity(intent)
    }

    fun medium(view: View) {
        val intent = Intent(MyApplication.appContext, Game::class.java).apply {
            putExtra(EXTRA_GAMETYPE, gameType)
            putExtra(EXTRA_OPPONENT, "cpu")
            putExtra(EXTRA_Difficulty, "medium")
        }
        startActivity(intent)
    }
}