package com.example.weatherapi


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val weatherApiServe by lazy {
        WeatherApiService.create()
    }
    var disposable: Disposable? = null

    lateinit var goButton: Button
    lateinit var plainText: EditText

    lateinit var temparature: TextView
    lateinit var location: TextView
    lateinit var weatherIcon: ImageView

    val apiID: String = "3e18a145ac4a79c53332e140e468779e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Input
        goButton = findViewById<Button>(R.id.go_button)
        plainText = findViewById<EditText>(R.id.editText)

        goButton.setOnClickListener {
            val text = plainText.text.toString()
            if (text.isNotEmpty()) {
                beginWeatherSearch(text)
            }
        }

        // Output
        location = findViewById<TextView>(R.id.location_text)
        temparature = findViewById<TextView>(R.id.temperature_text)
        weatherIcon = findViewById<ImageView>(R.id.weather_image)
    }

    private fun beginWeatherSearch(searchString: String) {
        disposable = weatherApiServe.tempCheck(searchString, apiID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> processFetchedData(result.main.temp, plainText.text.toString(), result.weather[0]) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() }
            )
    }

    private fun processFetchedData(temp: Double, loc: String, weather: WeatherModel.Weather) {
        val celcius: String = String.format("%.1f", temp - 273.15) + " °C"
        temparature.text = celcius
        location.text = loc

        var icon = when (weather.main) {
            "Rain" -> R.drawable.rain
            "Thunderstorm" -> R.drawable.thunderstorm
            "Clear" -> R.drawable.sunny
            else -> R.drawable.cloud
        }
        weatherIcon.setBackgroundResource(icon)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}

