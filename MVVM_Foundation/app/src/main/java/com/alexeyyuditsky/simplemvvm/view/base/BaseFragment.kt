package com.alexeyyuditsky.simplemvvm.view.base

import androidx.fragment.app.Fragment
import com.alexeyyuditsky.simplemvvm.MainActivity

/**
 * Base class for all fragments
 */
abstract class BaseFragment : Fragment() {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

    /**
     * Call this method when activity controls (e.g. toolbar) should be re-rendered
     * If you have more than 1 activity -> you should use a separate interface instead of direct
     cast to MainActivity
     */
    fun notifyScreenUpdates() = (requireActivity() as MainActivity).notifyScreenUpdates()

}