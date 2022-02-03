package com.alexeyyuditsky.test.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentMyBinding
import com.alexeyyuditsky.test.models.Squares
import com.alexeyyuditsky.test.viewModels.MainViewModel
import com.alexeyyuditsky.test.MyViewModelFactory

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    private val viewModel by viewModels<MainViewModel> { MyViewModelFactory(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)

        viewModel.squares.observe(viewLifecycleOwner) {
            renderSquares(it)
        }

        binding.generateColorsButton.setOnClickListener { viewModel.generateSquares() }

        return binding.root
    }

    private fun renderSquares(squares: Squares) = binding.apply {
        colorsContainer.removeAllViews()
        val identifiers = squares.colors.indices.map { View.generateViewId() }
        for (i in squares.colors.indices) {
            val row = i / squares.size
            val column = i % squares.size

            val view = View(context)
            view.setBackgroundColor(squares.colors[i])
            view.id = identifiers[i]

            val params = ConstraintLayout.LayoutParams(0, 0)
            params.setMargins(resources.getDimensionPixelSize(R.dimen.space))
            view.layoutParams = params

            // startToX constraint
            if (column == 0) params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            else params.startToEnd = identifiers[i - 1]

            // endToX constraint
            if (column == squares.size - 1) params.endToEnd =
                ConstraintLayout.LayoutParams.PARENT_ID
            else params.endToStart = identifiers[i + 1]

            // topToX constraint
            if (row == 0) params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            else params.topToBottom = identifiers[i - squares.size]

            // bottomToX constraint
            if (row == squares.size - 1) params.bottomToBottom =
                ConstraintLayout.LayoutParams.PARENT_ID
            else params.bottomToTop = identifiers[i + squares.size]

            colorsContainer.addView(view)
        }
    }

}