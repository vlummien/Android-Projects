package com.example.f1

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class DriverActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null
    lateinit var cardViewHam: CardView
    lateinit var driverImg: ImageView
    lateinit var driverNr: ImageView
    lateinit var driverName: TextView
    lateinit var card_Layout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)

        this.setTitle(intent.getStringExtra("name")) // Für Actionbar

        driverImg = findViewById(R.id.driver_image)
        driverNr = findViewById(R.id.driverNr)
        driverName = findViewById(R.id.driver_name)
        card_Layout = findViewById(R.id.cardLayout)

        driverImg.setImageResource(intent.getIntExtra("image", 1))
        driverNr.setImageResource(intent.getIntExtra("number", 1))
        driverName.setText(intent.getStringExtra("name"))
        card_Layout.setBackgroundResource(intent.getIntExtra("color", 1))

        cardViewHam = findViewById(R.id.cardview_ham)
        cardViewHam.setOnClickListener {
            play()
        }
    }

    fun play(){
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.ham_leftfront)
        mediaPlayer?.setOnCompletionListener {
            Toast.makeText(this, "complete", Toast.LENGTH_LONG).show()
            stopPlayer()
        }
        mediaPlayer?.start()
    }

    fun stopPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        Toast.makeText(this, "released", Toast.LENGTH_SHORT).show()

    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }
}