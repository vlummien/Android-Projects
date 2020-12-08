package com.example.f1

class Driver(val name: String, val image: Int, val numberImg: Int){
    // mp3 list
    // Poster-Bild & Mini-Bild

    fun getColor(): Int {
        var color = when (name) {
            "HAMILTON", "BOTTAS" -> R.drawable.mercedes_black
            "VERSTAPPEN" , "ALBON"-> R.drawable.red_bull
            "LECLERC", "VETTEL" -> R.drawable.ferrari
            "NORRIS", "SAINZ" -> R.drawable.mclaren
            "RICCIARDO", "OCON" -> R.drawable.renault
            "PEREZ", "STROLL", "HÜLKENBERG" -> R.drawable.racingpoint
            "GASLY", "KVYAT" -> R.drawable.alphatauri
            "RÄIKKÖNEN", "GIOVINAZZI" -> R.drawable.alfaromeo
            "GROSJEAN", "MAGNUSSEN" -> R.drawable.haas
            "LATIFI", "RUSSELL" -> R.drawable.williams
            else -> R.drawable.alphatauri
        }
        return color
    }

}