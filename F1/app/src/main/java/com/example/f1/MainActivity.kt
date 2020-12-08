package com.example.f1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drivers = createDrivers()


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = DriverListAdapter(this, drivers)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun createDrivers(): List<Driver> {
        var images: IntArray = intArrayOf(
            R.drawable.hamilton,R.drawable.bot, R.drawable.ver, R.drawable.norris, R.drawable.albon,
            R.drawable.ric, R.drawable.leclerc, R.drawable.stroll, R.drawable.perez,
            R.drawable.gasly, R.drawable.sainz, R.drawable.ocon, R.drawable.vettel,
            R.drawable.kvyat, R.drawable.hulk, R.drawable.raikkonen, R.drawable.gio,
            R.drawable.mag, R.drawable.lat, R.drawable.rus, R.drawable.gro
        )
        var driver_numbers: IntArray = intArrayOf(
            R.drawable.hamnr,R.drawable.botnr, R.drawable.vernr, R.drawable.nornr, R.drawable.albnr,
            R.drawable.ricnr, R.drawable.lecnr, R.drawable.strnr, R.drawable.pernr,
            R.drawable.gasnr, R.drawable.sainr, R.drawable.oconr, R.drawable.vetnr,
            R.drawable.kvynr, R.drawable.hulnr, R.drawable.rainr, R.drawable.gionr,
            R.drawable.magnr, R.drawable.latnr, R.drawable.rusnr, R.drawable.gronr
        )
        val driverNames: Array<String> = resources.getStringArray(R.array.driver)

        val drivers = mutableListOf<Driver>()
        for (position in 0 until 21) {
            val nextDriver = Driver(driverNames[position], images[position], driver_numbers[position])
            drivers.add(nextDriver)
        }
        return drivers
    }
}