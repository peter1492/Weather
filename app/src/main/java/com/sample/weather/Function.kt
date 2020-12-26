package com.sample.weather

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.util.*


class Function {


    companion object {

        //For temperature in Fahrenheit use units=imperial
        //For temperature in Celsius use units=metric

        private val OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=%s"
        private val OPEN_WEATHER_MAP_CITY_DATE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s"
        private val OPEN_WEATHER_MAP_7_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=current,minutely,hourly,alerts&units=%s"

        private val OPEN_WEATHER_MAP_API = "93cdb9f485c8bf9c9fd14856797b70df"



        fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long): String? {
            val id = actualId / 100
            var icon = ""
            if (actualId == 800) {
                val currentTime: Long = Date().time
                icon = if (currentTime >= sunrise && currentTime < sunset) {
                    "&#xf00d;"
                } else {
                    "&#xf02e;"
                }
            } else {
                when (id) {
                    2 -> icon = "&#xf01e;"
                    3 -> icon = "&#xf01c;"
                    7 -> icon = "&#xf014;"
                    8 -> icon = "&#xf013;"
                    6 -> icon = "&#xf01b;"
                    5 -> icon = "&#xf019;"
                }
            }
            return icon
        }

    }

    interface AsyncResponse {
        fun processFinish(output1: String?, output2: String?, output3: String?, output4: String?, output5: String?, output6: String?, output7: String?, output8: String?)
    }

    interface PlaceDaysAsyncResponse {
        fun processFinish(weatherReportArr : ArrayList<WeatherReport>)
    }

    class PlaceIdTask(asyncResponse: AsyncResponse?) : AsyncTask<String?, Void?, JSONObject?>() {
        var delegate: AsyncResponse? = null //Call back interface



         override fun doInBackground(vararg params: String?): JSONObject? {
            var jsonWeather: JSONObject? = null
            try {
                jsonWeather = getWeatherJSON(params[0], params[1], params[2])
            } catch (e: Exception) {
                Log.d("Weather", "Cannot process JSON results", e)
            }
            return jsonWeather
        }

        override fun onPostExecute(json: JSONObject?) {
            try {
                Log.d("Weather ", "Success ${json.toString()}")

                if (json != null) {
                    val details = json.getJSONArray("weather").getJSONObject(0)
                    val main = json.getJSONObject("main")
                    val df: DateFormat = DateFormat.getDateTimeInstance()
                    val city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country")
                    val description = details.getString("description").toUpperCase(Locale.US)
                    val temperature = String.format("%.2f", main.getDouble("temp")) + "°"
                    val humidity = main.getString("humidity") + "%"
                    val pressure = main.getString("pressure") + " hPa"
                    val updatedOn: String = df.format(Date(json.getLong("dt") * 1000))
                    val iconText: String? = setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)
                    delegate!!.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText, "" + json.getJSONObject("sys").getLong("sunrise") * 1000)
                }
            } catch (e: JSONException) {
                //Log.e(LOG_TAG, "Cannot process JSON results", e);
            }
        }

        init {
            delegate = asyncResponse //Assigning call back interfacethrough constructor
        }


        fun getWeatherJSON(lat: String?, lon: String?, unit : String? ): JSONObject? {
            return try {
                val url = URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon, unit))
                Log.d("Weather ", "url ${url}")

                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API)
                val reader = BufferedReader(
                        InputStreamReader(connection.inputStream))
                val json = StringBuffer(1024)
                var tmp: String? = ""
                while (reader.readLine().also({ tmp = it }) != null) json.append(tmp).append("\n")
                reader.close()
                val data = JSONObject(json.toString())
                Log.d("Weather ", "data ${data}")

                Log.d("Weather ", "Success ${data.getInt("cod")}")

                // This value will be 404 if the request was not successful
                if (data.getInt("cod") != 200) {
                    null
                } else data
            } catch (e: java.lang.Exception) {
                Log.d("Weather", "Error ${e.message}")

                null
            }
        }

    }



    class PlaceTimeTask(asyncResponse: AsyncResponse?) : AsyncTask<String?, Void?, JSONObject?>() {
        var delegate: AsyncResponse? = null //Call back interface



        override fun doInBackground(vararg params: String?): JSONObject? {
            var jsonWeather: JSONObject? = null
            try {
                jsonWeather = getWeatherJSON(params[0], params[1], params[2])
            } catch (e: Exception) {
                Log.d("Weather", "Cannot process JSON results", e)
            }
            return jsonWeather
        }

        override fun onPostExecute(json: JSONObject?) {
            try {
                Log.d("Weather ", "Success ${json.toString()}")

                if (json != null) {
                    val details = json.getJSONArray("weather").getJSONObject(0)
                    val main = json.getJSONObject("main")
                    val df: DateFormat = DateFormat.getDateTimeInstance()
                    val city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country")
                    val description = details.getString("description").toUpperCase(Locale.US)
                    val temperature = String.format("%.2f", main.getDouble("temp")) + "°"
                    val humidity = main.getString("humidity") + "%"
                    val pressure = main.getString("pressure") + " hPa"
                    val updatedOn: String = df.format(Date(json.getLong("dt") * 1000))
                    val iconText: String? = setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)
                    delegate!!.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText, "" + json.getJSONObject("sys").getLong("sunrise") * 1000)
                }
            } catch (e: JSONException) {
                //Log.e(LOG_TAG, "Cannot process JSON results", e);
            }
        }

        init {
            delegate = asyncResponse //Assigning call back interfacethrough constructor
        }


        fun getWeatherJSON(city: String?, date: String?, unit : String?): JSONObject? {
            return try {
                val url = URL(String.format(OPEN_WEATHER_MAP_CITY_DATE_URL, city, unit))
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API)
                val reader = BufferedReader(
                        InputStreamReader(connection.inputStream))
                val json = StringBuffer(1024)
                var tmp: String? = ""
                while (reader.readLine().also({ tmp = it }) != null) json.append(tmp).append("\n")
                reader.close()
                val data = JSONObject(json.toString())
                Log.d("Weather ", "Success ${data.getInt("cod")}")

                // This value will be 404 if the request was not successful
                if (data.getInt("cod") != 200) {
                    null
                } else data
            } catch (e: java.lang.Exception) {
                Log.d("Weather", "Error ${e.message}")

                null
            }
        }

    }


    class PlaceDaysTask(placeDaysAsyncResponse: PlaceDaysAsyncResponse?) : AsyncTask<String?, Void?, JSONObject?>() {
        var delegate: PlaceDaysAsyncResponse? = null //Call back interface



        override fun doInBackground(vararg params: String?): JSONObject? {
            var jsonWeather: JSONObject? = null
            try {
                jsonWeather = getWeatherJSON(params[0], params[1], params[2])
            } catch (e: Exception) {
                Log.d("Weather", "Cannot process JSON results", e)
            }
            return jsonWeather
        }

        override fun onPostExecute(json: JSONObject?) {
            try {

                if (json != null) {

                    val weatherList = json.getJSONArray("daily")
                    var weatherReportArr = arrayListOf<WeatherReport>()
                    for(i in 0 until weatherList.length()){
                        val listItem = weatherList.getJSONObject(i)

                        val details = listItem.getJSONArray("weather").getJSONObject(0)

                        val temp = listItem.getJSONObject("temp")
                        val tempDay = String.format("%.2f", temp.getDouble("day")) + "°"
                        val tempNight = String.format("%.2f", temp.getDouble("night")) + "°"

                        val description = details.getString("description").toUpperCase(Locale.US)
                        val humidity = listItem.getString("humidity") + "%"
                        val pressure = listItem.getString("pressure") + " hPa"
                        val city =  "" //json.getJSONObject("city").getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("city").getString("country")

                        val df: DateFormat = DateFormat.getDateTimeInstance()
                        val date: String = df.format(Date(listItem.getLong("dt") * 1000))

                        weatherReportArr.add(WeatherReport(city, description, humidity, pressure, "Day - $tempDay / Night - $tempNight", date ))

                    }

                    delegate!!.processFinish(weatherReportArr)
                }
            } catch (e: JSONException) {
                Log.e("LOG_TAG", "Cannot process JSON results", e)
            }
        }

        init {
            delegate = placeDaysAsyncResponse //Assigning call back interfacethrough constructor
        }


        fun getWeatherJSON(lat: String?, lon: String?, unit : String?): JSONObject? {
            return try {
                val url = URL(String.format(OPEN_WEATHER_MAP_7_URL, lat, lon, unit))
                Log.d("Weather ", "url ${url}")

                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API)
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val json = StringBuffer(1024)

                var tmp: String? = ""
                while (reader.readLine().also { tmp = it } != null) json.append(tmp).append("\n")
                reader.close()

                val statusCode: Int = connection.responseCode

                val data = JSONObject(json.toString())

                Log.d("Weather ", "Success ${statusCode}")

                // This value will be 404 if the request was not successful
                if (statusCode != 200) {
                    null
                } else data
            } catch (e: java.lang.Exception) {
                Log.d("Weather", "Error ${e.message} ")
                null
            }
        }

    }


    data class WeatherReport(val city: String, val description: String, val humidity: String, val pressure: String, val temp: String, val date: String)



}



