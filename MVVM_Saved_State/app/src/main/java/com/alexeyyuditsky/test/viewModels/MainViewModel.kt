package com.alexeyyuditsky.test.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.models.Squares
import kotlin.random.Random

class MainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _squares = savedStateHandle.getLiveData<Squares>(KEY_SAVED)
    val squares: LiveData<Squares> = _squares

    init {
        if (!savedStateHandle.contains(KEY_SAVED)) {
            savedStateHandle[KEY_SAVED] = createSquares()
        }
    }

    fun generateSquares() {
        _squares.value = createSquares()
    }

    private fun createSquares(): Squares = Squares(
        size = Random.nextInt(5, 11),
        colorProducer = { -Random.nextInt(0xFFFFFF) }
    )

    companion object {
        const val KEY_SAVED = "KEY_SAVED"
    }

}