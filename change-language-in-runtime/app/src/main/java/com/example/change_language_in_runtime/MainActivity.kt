package com.example.change_language_in_runtime

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LanguageFragment())
                .commit()
        }

        findViewById<Button>(R.id.buttonActivity).setOnClickListener { changeLanguage() }
    }

    private fun changeLanguage() {
        val currentLanguage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            resources.configuration.locales.get(0).language
        else
            resources.configuration.locale.language
        if (currentLanguage == "en")
            change("ru")
        else
            change("en")
    }

    private fun change(lang: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
    }

}

