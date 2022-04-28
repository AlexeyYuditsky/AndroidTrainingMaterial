package com.alexeyyuditsky.test.app

import android.os.Bundle
import android.util.Log
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.InMemoryBooksRepository
import com.alexeyyuditsky.test.app.view.books.BooksListFragment
import com.alexeyyuditsky.test.foundation.SingletonScopeDependencies
import com.alexeyyuditsky.test.foundation.model.coroutines.IoDispatcher
import com.alexeyyuditsky.test.foundation.sideeffects.SideEffectPluginsManager
import com.alexeyyuditsky.test.foundation.sideeffects.dialogs.plugin.DialogsPlugin
import com.alexeyyuditsky.test.foundation.sideeffects.intents.plugin.IntentsPlugin
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.plugin.NavigatorPlugin
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.plugin.StackFragmentNavigator
import com.alexeyyuditsky.test.foundation.sideeffects.permissions.plugin.PermissionsPlugin
import com.alexeyyuditsky.test.foundation.sideeffects.resources.plugin.ResourcesPlugin
import com.alexeyyuditsky.test.foundation.sideeffects.toasts.plugin.ToastsPlugin
import com.alexeyyuditsky.test.foundation.views.activity.BaseActivity

/**
 * This application is a single-activity app. MainActivity is a container
 * for all screens.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        InitializerDependencies.initDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun registerPlugins(manager: SideEffectPluginsManager) = manager.run {
        val navigator = createNavigator()
        register(ToastsPlugin())
        register(ResourcesPlugin())
        register(NavigatorPlugin(navigator))
        register(PermissionsPlugin())
        register(DialogsPlugin())
        register(IntentsPlugin())
    }

    private fun createNavigator() = StackFragmentNavigator(
        containerId = R.id.fragmentContainer,
        defaultTitle = getString(R.string.app_name),
        animations = StackFragmentNavigator.Animations(
            enterAnim = R.anim.enter,
            exitAnim = R.anim.exit,
            popEnterAnim = R.anim.pop_enter,
            popExitAnim = R.anim.pop_exit
        ),
        initialScreenCreator = BooksListFragment.Screen()
    )

}