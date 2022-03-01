package com.alexeyyuditsky.test.foundation.sideeffects.navigator.plugin

import com.alexeyyuditsky.test.foundation.sideeffects.SideEffectMediator
import com.alexeyyuditsky.test.foundation.views.BaseScreen
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.Navigator

class NavigatorSideEffectMediator : SideEffectMediator<Navigator>(), Navigator {

    override fun launch(screen: BaseScreen) = target {
        it.launch(screen)
    }

    override fun goBack(result: Any?) = target {
        it.goBack(result)
    }

    override fun goToMain(result: Any?) = target {
        it.goToMain(result)
    }
}