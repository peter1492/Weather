package com.sample.weather.ui.home

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.weather.R
import com.sample.weather.ui.user.Preferences

class HomeFragment : Fragment() {
    private var homeViewModel: HomeViewModel? = null
    private  var preferences : Preferences? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        preferences = Preferences.getPreferences(requireContext())

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val cityField = root.findViewById<TextView>(R.id.city_field)
        val updatedField = root.findViewById<TextView>(R.id.updated_field)
        val detailsField = root.findViewById<TextView>(R.id.details_field)
        val currentTemperatureField = root.findViewById<TextView>(R.id.current_temperature_field)
        val humidityField = root.findViewById<TextView>(R.id.humidity_field)
        val pressureField = root.findViewById<TextView>(R.id.pressure_field)
        val weatherIcon = root.findViewById<TextView>(R.id.weather_icon)


        homeViewModel!!.getWeatherCityField()?.observe(viewLifecycleOwner, Observer { cityField.text = it })
        homeViewModel!!.getWeatherDescription()?.observe(viewLifecycleOwner, Observer { detailsField.text = it })
        homeViewModel!!.getWeatherTemperature()?.observe(viewLifecycleOwner, Observer { currentTemperatureField.text = it })
        homeViewModel!!.getWeatherHumidity()?.observe(viewLifecycleOwner, Observer { humidityField.text = it })
        homeViewModel!!.getWeatherPressure()?.observe(viewLifecycleOwner, Observer { pressureField.text = it })
        homeViewModel!!.getWeatherUpdatedOn()?.observe(viewLifecycleOwner, Observer { updatedField.text = it })
        homeViewModel!!.getWeatherIconText()?.observe(viewLifecycleOwner, Observer { weatherIcon.text = (Html.fromHtml(it)) })

        homeViewModel!!.asyncTask.execute(preferences?.latitiude.toString(), preferences?.longitude.toString(), preferences?.temperature.toString());




        return root
    }
}