package com.sample.weather.ui.report

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sample.weather.Function
import com.sample.weather.R



class ReportItemRecyclerViewAdapter()
    : RecyclerView.Adapter<ReportItemRecyclerViewAdapter.ViewHolder>() {


    private var values = arrayListOf<Function.WeatherReport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
       holder.onBind(item)
    }

    override fun getItemCount(): Int = values.size

    fun  addItems(values: List<Function.WeatherReport>){
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateField = view.findViewById<TextView>(R.id.date_field)
        val updatedField = view.findViewById<TextView>(R.id.updated_field)
        val currentTemperatureField = view.findViewById<TextView>(R.id.current_temperature_field)
        val humidityField = view.findViewById<TextView>(R.id.humidity_field)
        val pressureField = view.findViewById<TextView>(R.id.pressure_field)


        fun onBind(item: Function.WeatherReport) {
            dateField.text = item.date
            currentTemperatureField.text = item.temp
            humidityField.text = item.humidity
            pressureField.text = item.pressure
            updatedField.text = item.description
        }
    }
}

