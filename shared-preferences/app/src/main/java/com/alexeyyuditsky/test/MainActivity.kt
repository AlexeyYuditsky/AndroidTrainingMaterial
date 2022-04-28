package com.alexeyyuditsky.test

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

const val APP_PREFERENCES = "APP_PREFERENCES"
const val KEY_PREF = "KEY_PREF"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
            if (key == KEY_PREF) {
                val value = preferences.getString(KEY_PREF, "")
                binding.currentValueFromSharedPreferencesTextView.text = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val currentValue = preferences.getString(KEY_PREF, "")
        binding.editText.setText(currentValue)
        binding.currentValueFromSharedPreferencesTextView.text = currentValue

        binding.button.setOnClickListener {
            val value = binding.editText.text.toString()
            preferences.edit()
                .putString(KEY_PREF, value)
                .apply()
        }

        preferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

}