package com.example.weatherapi

object WeatherModel {
    data class Result(val main: Main, val weather: List<Weather>)
    data class Weather(val id: Double, val main: String, val description: String, val icon: String)
    data class Main(val temp: Double)
}