package com.alexeyyuditsky.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val appSettingsPrefs by lazy { getSharedPreferences(APP_SETTING, 0) }
    private val sharedPrefsEdit by lazy { appSettingsPrefs.edit() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val isNightModeOn = checkNightMode()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (isNightModeOn) {
                sharedPrefsEdit.putBoolean(NIGHT_MODE, false).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                sharedPrefsEdit.putBoolean(NIGHT_MODE, true).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun checkNightMode(): Boolean {
        val isNightModeOn = appSettingsPrefs.getBoolean(NIGHT_MODE, false)
        if (isNightModeOn) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return isNightModeOn
    }

    private companion object {
        const val APP_SETTING = "app_setting_prefs"
        const val NIGHT_MODE = "night_mode"

        fun <T> log(message: T) {
            Log.d("MyLog", message.toString())
        }
    }

}