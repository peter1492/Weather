package com.sample.weather.ui.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sample.weather.MainActivity
import com.sample.weather.R


class LoginActivity : AppCompatActivity(), LocationListener {

    private lateinit var userName : AppCompatEditText
    private  var preferences : Preferences? = null
    private var locationManager: LocationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userName = findViewById(R.id.tvUserName)
        preferences = Preferences.getPreferences(this)

        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager


        if(!TextUtils.isEmpty(preferences?.userName)) {
            userName.setText(preferences?.userName)
        }

        askForPermission()


    }

    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, 99)
        }else{
            requestLoc()
        }
    }



    fun onNext(view: View) {
        if(!TextUtils.isEmpty(userName.text)) {
            if(preferences?.latitiude == 0F || preferences?.longitude == 0F){
                Toast.makeText(this, "Please enable GPS !", Toast.LENGTH_SHORT).show()
            }else{
                //enter dashboard
                preferences?.userName = userName.text.toString()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }else{
            Toast.makeText(this, "Please Enter a your name!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLoc()
        }
    }

    fun requestLoc(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_COARSE //default
            criteria.isCostAllowed = false
            val provider = locationManager?.getBestProvider(criteria, false)
            locationManager!!.requestLocationUpdates(provider!!, 0, 0f, this@LoginActivity)
        }
    }

    override fun onLocationChanged(loc: Location?) {
        val tvloc = findViewById<AppCompatTextView>(R.id.tvloc)
        tvloc.text = "${loc?.latitude?.toFloat()}  ${loc?.longitude?.toFloat()}"
        preferences?.latitiude = loc?.latitude?.toFloat()
        preferences?.longitude = loc?.longitude?.toFloat()
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }
}