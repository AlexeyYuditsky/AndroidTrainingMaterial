package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }

}