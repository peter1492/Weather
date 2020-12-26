package com.sample.weather.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.weather.Function

class HomeViewModel : ViewModel() {
    private val weatherCity  = MutableLiveData<String>()
    private val weatherDescription = MutableLiveData<String>()
    private val weatherTemperature = MutableLiveData<String>()
    private val weatherHumidity = MutableLiveData<String>()
    private val weatherPressure  = MutableLiveData<String>()
    private val weatherUpdateOn = MutableLiveData<String>()
    private val weatherIconText = MutableLiveData<String>()
    private val weatherSunrise = MutableLiveData<String>()


    fun getWeatherCityField(): LiveData<String?>? {
       return weatherCity
    }

    fun getWeatherDescription(): LiveData<String?>? {
        return weatherDescription
    }

    fun getWeatherTemperature(): LiveData<String?>? {
        return weatherTemperature
    }

    fun getWeatherHumidity(): LiveData<String?>? {
        return weatherHumidity
    }

    fun getWeatherPressure(): LiveData<String?>? {
        return weatherPressure
    }

    fun getWeatherUpdatedOn(): LiveData<String?>? {
        return weatherUpdateOn
    }

    fun getWeatherIconText(): LiveData<String?>? {
        return weatherIconText
    }

    fun getWeatherSunRise(): LiveData<String?>? {
        return weatherSunrise
    }

    var asyncTask = Function.PlaceIdTask(object : Function.AsyncResponse{
        override fun processFinish( weather_city : String?,  weather_description : String?,  weather_temperature : String?,
                                    weather_humidity : String?,  weather_pressure : String?,  weather_updatedOn: String?,
                                    weather_iconText: String?, sun_rise: String?) {

            weatherCity?.setValue(weather_city)
            weatherDescription?.setValue(weather_description)
            weatherTemperature?.setValue(weather_temperature)
            weatherHumidity?.setValue(weather_humidity)
            weatherPressure?.setValue(weather_pressure)
            weatherUpdateOn?.setValue(weather_updatedOn)
            weatherIconText?.setValue(weather_iconText)
            weatherSunrise?.setValue(sun_rise)

        }

    })

    
}