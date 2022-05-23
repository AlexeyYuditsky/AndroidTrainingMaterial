package com.alexeyyuditsky.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.screens.main.tabs.TabsFragment
import com.alexeyyuditsky.test.utils.viewModelCreator
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModelCreator { MainActivityViewModel(Repositories.accountsRepository) }

    private var navController: NavController? = null

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment || navController == f.findNavController()) return
            navController?.removeOnDestinationChangedListener(destinationListener)
            navController = f.findNavController()
            navController?.addOnDestinationChangedListener(destinationListener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        setSupportActionBar(binding.toolbar)
        findNavController()
        setNavControllerListener()

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)

        viewModel.username.observe(this) {
            binding.toolbarTextView.text = it
        }
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        val setOfStartScreens = setOf(
            navController?.currentDestination?.parent?.startDestinationId,
            R.id.signInFragment
        )
        if (setOfStartScreens.contains(navController?.currentDestination?.id)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()
    }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.tabsFragment) return@OnDestinationChangedListener
            binding.toolbar.isGone = (destination.id == R.id.splashFragment)
            supportActionBar?.title = prepareTitle(destination.label, arguments)
            val setOfStartScreens = setOf(
                destination.parent?.startDestinationId,
                R.id.signInFragment,
                R.id.tabsFragment,
            )
            supportActionBar?.setDisplayHomeAsUpEnabled(!setOfStartScreens.contains(destination.id))
        }

    private fun setNavControllerListener() {
        navController?.addOnDestinationChangedListener(destinationListener)
    }

    private fun findNavController() {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {
        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments[argName].toString())
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }

}