package com.alexeyyuditsky.pixabayapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.pixabayapp.data.Photo
import com.alexeyyuditsky.pixabayapp.domain.PhotosInteracor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val photosInteractor: PhotosInteracor
) : ViewModel() {

    private val _photosLiveData = MutableLiveData<List<Photo>>()
    val photoLiveData: LiveData<List<Photo>> get() = _photosLiveData

    init {
        fetchPhotos()
    }

    fun fetchPhotos(query: String = "girl") = viewModelScope.launch(Dispatchers.IO) {
        val photos: List<Photo> = photosInteractor.fetchPhotos(query).hits
        withContext(Dispatchers.Main) {
            _photosLiveData.value = photos
        }
    }

}