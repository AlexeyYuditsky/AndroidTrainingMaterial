package com.alexeyyuditsky.pixabayapp.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexeyyuditsky.pixabay_app.R
import com.alexeyyuditsky.pixabay_app.databinding.FragmentMainBinding
import com.alexeyyuditsky.pixabayapp.core.App
import com.alexeyyuditsky.pixabayapp.core.Const
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)

        val adapter = PhotosAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        val viewModel by viewModels<MainViewModel>()
        viewModel.photoLiveData.observe(requireActivity()) { photos ->
            adapter.updatePhotos(photos)
        }

        var job: Job? = null
        binding.searchEditText.addTextChangedListener { search: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(Const.SEARCH_DELAY)
                if (search.toString().isBlank()) return@launch
                viewModel.fetchPhotos(search.toString().trim())
            }
        }
    }

}