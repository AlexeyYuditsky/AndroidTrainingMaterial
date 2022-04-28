package com.alexeyyuditsky.foundation.navigator

import com.alexeyyuditsky.foundation.utils.ResourceActions
import com.alexeyyuditsky.foundation.views.BaseScreen

/** Mediator that holds navigation actions in the queue if real navigator is not active. */
class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(screen: BaseScreen) = targetNavigator {
        it.launch(screen = screen)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result = result)
    }

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() = targetNavigator.clear()

}