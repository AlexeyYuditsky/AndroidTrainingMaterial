package com.alexeyyuditsky.test.foundation.navigator

import com.alexeyyuditsky.test.foundation.utils.ResourceActions
import com.alexeyyuditsky.test.foundation.views.BaseScreen

/** Mediator that holds navigation actions in the queue if real navigator is not active. */
class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(screen: BaseScreen) = targetNavigator {
        it.launch(screen = screen)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result = result)
    }

    override fun goToMain() = targetNavigator {
        it.goToMain()
    }

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() = targetNavigator.clear()

}