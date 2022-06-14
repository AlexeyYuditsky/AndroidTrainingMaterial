package com.alexeyyuditsky.test.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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

    private val viewModel by viewModelCreator { ListViewModel(Repositories.employeesRepository) }

    private lateinit var binding: FragmentListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        val adapter = EmployeesAdapter()
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.recyclerView.adapter = adapterWithLoadState
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        observeEmployees(adapter)
        observeLoadState(adapter)
        setupSwipeToRefresh()
    }

    private fun observeEmployees(adapter: EmployeesAdapter) = lifecycleScope.launch {
        viewModel.employeesFlow.collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeLoadState(adapter: EmployeesAdapter) {
        adapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

}