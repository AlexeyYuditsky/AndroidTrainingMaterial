package com.alexeyyuditsky.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.screens.MyFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) launchStartFragment()
    }

    private fun launchStartFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MyFragment())
            .commit()
    }

}