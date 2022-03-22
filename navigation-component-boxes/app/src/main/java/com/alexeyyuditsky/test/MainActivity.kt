package com.alexeyyuditsky.test

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexeyyuditsky.test.contract.*
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.fragments.*
import com.alexeyyuditsky.test.model.Options

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment) return
            updateUI(f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        setSupportActionBar(binding.toolbar)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /*------------------------ implementation interface Navigator -------------------------------*/

    override fun <T : Parcelable> publishResult(result: T) {
        navController.previousBackStackEntry?.savedStateHandle?.set(MenuFragment.KEY_RESULT, result)
    }

    override fun <T : Parcelable> listenResult(
        key: String,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        val livedata =
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<T>(MenuFragment.KEY_RESULT)
        livedata?.observe(owner) { result ->
            if (livedata.value == null) return@observe
            listener(result)
            livedata.value = null
        }
    }

    override fun showCongratulationsScreen() {
        launchDestination(R.id.action_boxSelectionFragment_to_boxFragment)
    }

    override fun showBoxSelectionScreen(options: Options) {
        launchDestination(
            R.id.action_menuFragment_to_boxSelectionFragment,
            BoxSelectionFragment.createArgs(options)
        )
    }

    override fun showOptionsScreen(options: Options) {
        launchDestination(
            R.id.action_menuFragment_to_optionsFragment,
            OptionsFragment.createArgs(options)
        )
    }

    override fun showAboutScreen() {
        launchDestination(R.id.action_menuFragment_to_aboutFragment)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        navController.popBackStack(R.id.menuFragment, false)
    }

    /*------------------------ implementation interface Navigator -------------------------------*/

    private fun launchDestination(destinationId: Int, args: Bundle? = null) {
        navController.navigate(destinationId, args)
    }

    private fun updateUI(fragment: Fragment) {
        if (fragment is HasCustomTitle) {
            binding.toolbar.title = getString(fragment.getTitleRes())
        } else {
            binding.toolbar.title = getString(R.string.fragment_navigation_example)
        }

        if (navController.currentDestination?.id == navController.graph.startDestinationId) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        if (fragment is HasCustomAction) {
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            binding.toolbar.menu.clear()
        }
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        val menuItem = binding.toolbar.menu.add(action.textRes)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }

}

