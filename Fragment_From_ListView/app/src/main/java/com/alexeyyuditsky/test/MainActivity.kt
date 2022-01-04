package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.test.contract.AppContract
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.model.Cat
import com.alexeyyuditsky.test.model.CatsService

class MainActivity : AppCompatActivity(), AppContract {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) createFragment()
    }

    private fun createFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, CatsListFragment())
            .commit()
    }

    override val catsService: CatsService
        get() = (applicationContext as App).catsService

    override fun launchCatDetailsScreen(cat: Cat) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CatDetailsFragment.newInstance(cat))
            .addToBackStack(null)
            .commit()
    }

}