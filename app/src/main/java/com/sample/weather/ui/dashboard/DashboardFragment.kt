package com.sample.weather.ui.dashboard

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.weather.R
import com.sample.weather.ui.user.Preferences
import java.util.*



class DashboardFragment : Fragment() , AdapterView.OnItemSelectedListener, DatePicker.OnDateChangedListener {
    private var dashboardViewModel: DashboardViewModel? = null
    private  var preferences : Preferences? = null


    private var spStatePicker : Spinner? = null
    private var datePicker : DatePicker? = null

    private var iCurrentStateSelection : Int? = 0
    private var iCurrentDateSelection : Long? = 0


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.fragment_dashboard, container, false)

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        preferences = Preferences.getPreferences(requireContext())

        val cityField = root.findViewById<TextView>(R.id.city_field)
        val updatedField = root.findViewById<TextView>(R.id.updated_field)
        val detailsField = root.findViewById<TextView>(R.id.details_field)
        val currentTemperatureField = root.findViewById<TextView>(R.id.current_temperature_field)
        val humidityField = root.findViewById<TextView>(R.id.humidity_field)
        val pressureField = root.findViewById<TextView>(R.id.pressure_field)
        val weatherIcon = root.findViewById<TextView>(R.id.weather_icon)




        spStatePicker = root.findViewById(R.id.spState)
        spStatePicker?.onItemSelectedListener = this


        datePicker = root.findViewById(R.id.datePicker)


        val c: Calendar = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datePicker?.init(year, month, day, this)


        iCurrentStateSelection = spStatePicker?.selectedItemPosition
        iCurrentDateSelection = c.timeInMillis

        dashboardViewModel?.getWeatherCityField()?.observe(viewLifecycleOwner, Observer { cityField.text = it })
        dashboardViewModel?.getWeatherDescription()?.observe(viewLifecycleOwner, Observer { detailsField.text = it })
        dashboardViewModel?.getWeatherTemperature()?.observe(viewLifecycleOwner, Observer { currentTemperatureField.text = it })
        dashboardViewModel?.getWeatherHumidity()?.observe(viewLifecycleOwner, Observer { humidityField.text = it })
        dashboardViewModel?.getWeatherPressure()?.observe(viewLifecycleOwner, Observer { pressureField.text = it })
        dashboardViewModel?.getWeatherUpdatedOn()?.observe(viewLifecycleOwner, Observer { updatedField.text = it })
        dashboardViewModel?.getWeatherIconText()?.observe(viewLifecycleOwner, Observer { weatherIcon.text = (Html.fromHtml(it)) })


        getWeatherData(spStatePicker?.getItemAtPosition(iCurrentStateSelection ?: 0).toString(), "")
        return root

    }

    private fun getWeatherData(city : String, dt : String = ""){
        dashboardViewModel?.asyncTask()?.execute(city,dt, preferences?.temperature.toString());
    }









    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        if (iCurrentStateSelection != pos){
            //get date / get state and call change in weather
            getWeatherData(parent?.getItemAtPosition(pos).toString())
        }
        iCurrentStateSelection = pos

    }


    override fun onDateChanged(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val c = Calendar.getInstance()
        c.set(p1, p2 ,p3)
        iCurrentDateSelection = c.timeInMillis

        //get date / get state and call change in weather

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}


