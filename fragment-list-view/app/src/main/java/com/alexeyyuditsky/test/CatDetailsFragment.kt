package com.alexeyyuditsky.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.databinding.FragmentCatDetailsBinding
import com.alexeyyuditsky.test.model.Cat

class CatDetailsFragment : Fragment() {

    private val cat: Cat
        get() = requireArguments().getParcelable(ARG_CAT)!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCatDetailsBinding.inflate(inflater, container, false).apply {

           nameTextView.text = cat.name
           descriptionTextView.text = cat.description

        }.root
    }

    companion object {
        private const val ARG_CAT = "ARG_CAT"

        fun newInstance(cat: Cat): CatDetailsFragment = CatDetailsFragment().apply {
            arguments = bundleOf(ARG_CAT to cat)
        }
    }

}