package com.alexeyyuditsky.test.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeyyuditsky.test.navigator.ARG_SCREEN
import com.alexeyyuditsky.test.navigator.MainNavigator
import com.alexeyyuditsky.test.navigator.Navigator

// класс который создаёт viewModels,
class ViewModelFactory(
    private val screen: BaseScreen,
    private val fragment: BaseFragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider =
            ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navigatorProvider.get(MainNavigator::class.java)

        val constructor = modelClass.getConstructor(Navigator::class.java, screen::class.java)
        return constructor.newInstance(navigator, screen)
    }

}

// функция которая позволяет создавать в каждом экране viewModels
inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    // screen: BaseScreen = EditFragment.Screen()
    val screen = requireArguments().getParcelable<BaseScreen>(ARG_SCREEN) as BaseScreen
    ViewModelFactory(screen, this)
}