package com.example.weatherapi

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun tempCheck(@Query("q") location: String,
                  @Query("appid") apiId: String):
            Observable<WeatherModel.Result>

    companion object {
        fun create(): WeatherApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}