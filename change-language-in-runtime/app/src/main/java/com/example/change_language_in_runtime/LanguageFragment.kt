package com.example.change_language_in_runtime

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment

class LanguageFragment : Fragment(R.layout.fragment_language) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().findViewById<Button>(R.id.buttonFragment)
            .setOnClickListener { changeLanguage() }
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