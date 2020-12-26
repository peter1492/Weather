package com.sample.weather.ui.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.weather.Function
import java.util.ArrayList


class ReportItemViewModel : ViewModel() {

    private val weatherReportArr  = MutableLiveData<List<Function.WeatherReport>>()

    fun getWeatherReportArr(): LiveData<List<Function.WeatherReport>>? {
        return weatherReportArr
    }



    var asyncTask = Function.PlaceDaysTask(object : Function.PlaceDaysAsyncResponse{
        override fun processFinish(result: ArrayList<Function.WeatherReport>) {

            weatherReportArr.value = result
        }
    })
}