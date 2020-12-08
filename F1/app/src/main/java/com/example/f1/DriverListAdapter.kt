package com.example.f1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DriverListAdapter(val context: Context, val drivers: List<Driver>):
        RecyclerView.Adapter<DriverListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.driver_name)
        val image: ImageView = itemView.findViewById(R.id.driver_image)

        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.mainLayout)
        val driverCard: ConstraintLayout = itemView.findViewById(R.id.driverCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var view: View = inflater.inflate(R.layout.driver_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(drivers[position].image)
        holder.name.setText(drivers[position].name)

        holder.driverCard.setBackgroundResource(drivers[position].getColor())


        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, DriverActivity::class.java).apply {
                putExtra("name", drivers[position].name)
                putExtra("image", drivers[position].image)
                putExtra("number", drivers[position].numberImg)
                putExtra("color", drivers[position].getColor())
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

}