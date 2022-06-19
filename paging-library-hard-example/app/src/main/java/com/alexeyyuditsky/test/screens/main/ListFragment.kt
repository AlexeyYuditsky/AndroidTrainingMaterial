package com.alexeyyuditsky.test.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.adapters.DefaultLoadStateAdapter
import com.alexeyyuditsky.test.adapters.EmployeesAdapter
import com.alexeyyuditsky.test.adapters.TryAgainAction
import com.alexeyyuditsky.test.databinding.FragmentListBinding
import com.alexeyyuditsky.test.utils.viewModelCreator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel by viewModelCreator<ListViewModel> { ListViewModel(Repositories.employeesRepository) }

    private lateinit var binding: FragmentListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        val adapter = EmployeesAdapter(viewModel)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.recyclerView.adapter = adapterWithLoadState
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.loadStateView.tryAgainButton.setOnClickListener { adapter.retry() }

        observeEmployees(adapter)
        observeLoadState(adapter)
        observeEditText()
        observeCheckBox()
        observeSwipeToRefresh()
        observeInvalidateList(adapter)
        observeErrorMessages()
        handleScrollingToTopWhenSearching(adapter)
        handleListVisibility(adapter)
    }

    private fun observeErrorMessages() {
        viewModel.errorEvents.observe(viewLifecycleOwner) {
            it.get()?.let { messageRes -> Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun observeInvalidateList(adapter: EmployeesAdapter) =
        viewModel.invalidateListEvent.observe(viewLifecycleOwner) {
            it.get()?.let { adapter.refresh() }
        }

    private fun observeEmployees(adapter: EmployeesAdapter) = lifecycleScope.launch {
        viewModel.employeesFlow.collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    private fun observeSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeLoadState(adapter: EmployeesAdapter) = adapter.addLoadStateListener {
        binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
    }

    private fun observeCheckBox() {
        val errorCheckBox = requireActivity().findViewById<CheckBox>(R.id.errorCheckBox)
        lifecycleScope.launch {
            viewModel.isErrorsEnabled.collectLatest {
                errorCheckBox.isChecked = it
            }
        }
        errorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setEnableError(isChecked)
        }
    }

    private fun observeEditText() {
        val editText = requireActivity().findViewById<EditText>(R.id.searchEditText)
        editText.addTextChangedListener {
            viewModel.searchByName(it.toString())
        }
    }

    private fun getRefreshLoadStateFlow(adapter: EmployeesAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }

    private fun handleScrollingToTopWhenSearching(adapter: EmployeesAdapter) = lifecycleScope.launch {
        val state = getRefreshLoadStateFlow(adapter)
        val items = List<LoadState?>(2) { null }
        state.scan(items) { previous, value -> previous.drop(1) + value }
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.recyclerView.scrollToPosition(0)
                }
            }
    }

    private fun handleListVisibility(adapter: EmployeesAdapter) = lifecycleScope.launch {
        val state = getRefreshLoadStateFlow(adapter)
        val items = List<LoadState?>(3) { null }
        state.scan(items) { previous, value -> previous.drop(1) + value }
            .collectLatest { (beforePrevious, previous, current) ->
                binding.recyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
                binding.loadStateView.messageTextView.isVisible = current is LoadState.Error
                binding.loadStateView.tryAgainButton.isVisible = current is LoadState.Error
            }
    }

}
