package com.alexeyyuditsky.test.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.view.books.BooksListFragment
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.foundation.ActivityScopeViewModel
import com.alexeyyuditsky.test.foundation.navigator.IntermediateNavigator
import com.alexeyyuditsky.test.foundation.navigator.StackFragmentNavigator
import com.alexeyyuditsky.test.foundation.uiactions.AndroidUIActions
import com.alexeyyuditsky.test.foundation.utils.viewModelCreator
import com.alexeyyuditsky.test.foundation.views.FragmentsHolder

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentsHolder {

    private lateinit var navigator: StackFragmentNavigator

    private val activityViewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            uiActions = AndroidUIActions(applicationContext),
            navigator = IntermediateNavigator()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = StackFragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContainer,
            defaultTitle = getString(R.string.app_name),
            animations = StackFragmentNavigator.Animations(
                R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit,
            ),
            initialScreen = BooksListFragment.Screen(),
        )
        navigator.onCreate(savedInstanceState = savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel = activityViewModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}