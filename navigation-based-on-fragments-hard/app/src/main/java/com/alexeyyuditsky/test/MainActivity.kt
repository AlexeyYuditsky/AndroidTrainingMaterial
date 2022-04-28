package com.alexeyyuditsky.test

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test.contract.*
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.fragments.*
import com.alexeyyuditsky.test.model.Options

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
            updateUI(f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) launchMenuFragment()

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /*------------------------ implementation interface Navigator -------------------------------*/

    // Фиксируем результат работы фрагмента, чтобы позже получить его в MenuFragment
    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(
            result.javaClass.name,
            bundleOf(KEY_RESULT to result)
        )
    }

    // Получаем сохраненный результат работы фрагмента через лямбда-функцию listener в MenuFragment
    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            clazz.name,
            owner,
            { _, bundle -> listener(bundle.getParcelable(KEY_RESULT)!!) })
    }

    override fun showCongratulationsScreen(): Unit = launchFragment(BoxFragment())

    override fun showBoxSelectionScreen(options: Options) {
        launchFragment(BoxSelectionFragment.newInstance(options))
    }

    override fun showOptionsScreen(options: Options) {
        launchFragment(OptionsFragment.newInstance(options))
    }

    override fun showAboutScreen(): Unit = launchFragment(AboutFragment())
    override fun goBack(): Unit = onBackPressed()
    override fun goToMenu() {
        return supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /*------------------------ implementation interface Navigator -------------------------------*/

    private fun launchMenuFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MenuFragment())
            .commit()
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun updateUI(fragment: Fragment) {
        if (fragment is HasCustomTitle) binding.toolbar.title = getString(fragment.getTitleRes())
        else binding.toolbar.title = getString(R.string.fragment_navigation_example)

        if (supportFragmentManager.backStackEntryCount > 0)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        else supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (fragment is HasCustomAction) createCustomToolbarAction(fragment.getCustomAction())
        else binding.toolbar.menu.clear()
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

    companion object {
        private const val KEY_RESULT = "KET_RESULT"
    }

}