package com.alexeyyuditsky.simplemvvm.view.changecolor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.alexeyyuditsky.simplemvvm.R
import com.alexeyyuditsky.simplemvvm.databinding.FragmentChangeColorBinding
import com.alexeyyuditsky.foundation.views.HasScreenTitle
import com.alexeyyuditsky.foundation.views.BaseFragment
import com.alexeyyuditsky.foundation.views.BaseScreen
import com.alexeyyuditsky.foundation.views.screenViewModel
import com.alexeyyuditsky.simplemvvm.view.onTryAgain
import com.alexeyyuditsky.simplemvvm.view.renderSimpleResult

/** Screen for changing color.
 * 1) Displays the list of available colors
 * 2) Allows choosing the desired color
 * 3) Chosen color is saved only after pressing "Save" button
 * 4) The current choice is saved via [SavedStateHandle] (see [ChangeColorViewModel]) */
class ChangeColorFragment : BaseFragment(), HasScreenTitle {

    /** This screen has 1 argument: color ID to be displayed as selected. */
    class Screen(val currentColorId: Long) : BaseScreen()

    override val viewModel by screenViewModel<ChangeColorViewModel>()

    /** Example of dynamic screen title */
    override fun getScreenTitle(): String? {
        Log.d("MyLog", viewModel.screenTitle.value.toString())
        return viewModel.screenTitle.value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentChangeColorBinding.inflate(inflater, container, false)

        val adapter = ColorsAdapter(viewModel)
        setupLayoutManager(binding, adapter)

        viewModel.viewState.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(root = binding.root, result = result) {
                adapter.items = it.colorsList
                binding.saveButton.visibility =
                    if (it.showSaveButton) View.VISIBLE else View.INVISIBLE
                binding.cancelButton.isVisible = it.showCancelButton
                binding.saveProgressBar.isVisible = it.showSaveProgressBar
            }
        }

        viewModel.screenTitle.observe(viewLifecycleOwner) {
            // if screen title is changed -> need to notify activity about updates
            notifyScreenUpdates()
        }

        onTryAgain(binding.root) {
            viewModel.tryAgain()
        }

        binding.saveButton.setOnClickListener { viewModel.onSavePressed() }
        binding.cancelButton.setOnClickListener { viewModel.onCancelPressed() }

        return binding.root
    }

    private fun setupLayoutManager(binding: FragmentChangeColorBinding, adapter: ColorsAdapter) {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = binding.root.width
                val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
                val columns = width / itemWidth // 1080 / 315 = 3.4
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
            }
        })
    }

}