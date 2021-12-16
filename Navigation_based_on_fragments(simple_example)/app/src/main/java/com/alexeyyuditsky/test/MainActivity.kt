package com.alexeyyuditsky.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.github.javafaker.Faker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val faker = Faker.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            val fragment = CounterFragment.newInstance(counterValue = 1, quote = createQuote())
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    fun launchNext() {
        val fragment = CounterFragment.newInstance(
            counterValue = getScreenCount() + 1,
            quote = createQuote()
        )
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun getScreenCount(): Int = supportFragmentManager.backStackEntryCount + 1

    private fun createQuote(): String = faker.harryPotter().quote()
}