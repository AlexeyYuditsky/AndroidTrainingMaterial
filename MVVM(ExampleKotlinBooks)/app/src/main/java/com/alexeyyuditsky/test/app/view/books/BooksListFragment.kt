package com.alexeyyuditsky.test.app.view.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.app.view.collectFlow
import com.alexeyyuditsky.test.app.view.onTryAgain
import com.alexeyyuditsky.test.app.view.renderSimpleResult
import com.alexeyyuditsky.test.databinding.FragmentBookListBinding
import com.alexeyyuditsky.test.foundation.views.BaseFragment
import com.alexeyyuditsky.test.foundation.views.BaseScreen
import com.alexeyyuditsky.test.foundation.views.screenViewModel

class BooksListFragment : BaseFragment() {

    class Screen : BaseScreen()

    override val viewModel by screenViewModel<BooksListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentBookListBinding.inflate(inflater, container, false)

        val adapter = BooksAdapter(viewModel)
        binding.recyclerView.adapter = adapter

        val dividerItem = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItem)

        viewModel.booksList.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = { adapter.items = it }
            )
        }

        onTryAgain(binding.root) { viewModel.tryAgain() }

        return binding.root
    }

}