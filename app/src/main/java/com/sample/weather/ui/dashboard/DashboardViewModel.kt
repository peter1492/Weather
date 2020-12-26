package com.sample.weather.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.weather.Function

class DashboardViewModel : ViewModel() {
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

    fun asyncTask() : Function.PlaceTimeTask  {
        return  Function.PlaceTimeTask(object : Function.AsyncResponse{
            override fun processFinish( weather_city : String?,  weather_description : String?,  weather_temperature : String?,
                                        weather_humidity : String?,  weather_pressure : String?,  weather_updatedOn: String?,
                                        weather_iconText: String?, sun_rise: String?) {

                weatherCity.value = weather_city
                weatherDescription.value = weather_description
                weatherTemperature.value = weather_temperature
                weatherHumidity.value = weather_humidity
                weatherPressure.value = weather_pressure
                weatherUpdateOn.value = weather_updatedOn
                weatherIconText.value = weather_iconText
                weatherSunrise.value = sun_rise

            }

        })
    }

}