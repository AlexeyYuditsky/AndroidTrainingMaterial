package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        if (savedInstanceState == null) createFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun createFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, RandomFragment.newInstance(generateUuid()))
            .commit()
    }

    override fun launchNext() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, RandomFragment.newInstance(generateUuid()))
            .commit()
    }

    override fun generateUuid(): String = UUID.randomUUID().toString()

    override fun update() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment is HasUuid)
            binding.currentFragmentUuidTextView.text = currentFragment.getUuid()

        if (currentFragment is NumberListener)
            currentFragment.onNewScreenNumber(1 + supportFragmentManager.backStackEntryCount)
    }
}