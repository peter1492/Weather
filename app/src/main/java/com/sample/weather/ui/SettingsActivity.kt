package com.sample.weather.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sample.weather.R
import com.sample.weather.ui.user.Preferences


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val preferences = Preferences.getPreferences(requireContext())


            val mTempPreference = preferenceManager.findPreference<Preference>("temperature") as ListPreference?
            val mNamePreference = preferenceManager.findPreference<Preference>("signature") as EditTextPreference?

            mTempPreference?.setValueIndex(if(preferences?.temperature == "metric")  1 else 0)
            mNamePreference?.text = preferences?.userName




            val onPreferenceChangeListener = Preference.OnPreferenceChangeListener { p, newValue ->
                when(p.key){
                    "temperature" -> {
                        preferences?.temperature = newValue.toString()
                    }
                    "signature" -> {
                        preferences?.userName = newValue.toString()
                    }
                }
                true
            }

            mTempPreference!!.onPreferenceChangeListener = onPreferenceChangeListener
            mNamePreference!!.onPreferenceChangeListener = onPreferenceChangeListener
        }
    }
}