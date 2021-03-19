package com.zulham.gitroom.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.zulham.gitroom.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(){

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            changeLanguage()
            reminderAlarm()

        }

        private fun reminderAlarm() {
            val switchPreference = findPreference<Preference>(getString(R.string.ALARM)) as SwitchPreference
            switchPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->

                val value = newValue as Boolean

                if (value) {
                    Toast.makeText(context, "Sorry this feature can't use now \n Alarm Activated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Sorry this feature can't use now \n Alarm Deactivated", Toast.LENGTH_SHORT).show()
                }

                return@OnPreferenceChangeListener true

            }
        }

        private fun changeLanguage() {
            val preference = findPreference<Preference>(getString(R.string.LANGUAGE))
            preference?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        }

    }
}