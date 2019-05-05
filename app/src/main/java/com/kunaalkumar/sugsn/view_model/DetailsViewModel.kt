package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbMovieItem
import com.kunaalkumar.sugsn.tmdb.TMDbVideos

class DetailsViewModel : ViewModel() {
    val TAG: String = "Sugsn@DetailsViewModel"

    private var currentCallback = MediatorLiveData<TMDbMovieItem>()
    private var movieVideosCallback = MediatorLiveData<TMDbVideos>()
    private var movieVideos = MediatorLiveData<TMDbVideos>()

    private val _name = MutableLiveData<String>()
    private val _rating = MutableLiveData<String>()
    private val _image = MutableLiveData<String>()

    val name: LiveData<String> = _name
    val rating: LiveData<String> = _rating
    val image: LiveData<String> = _image

    fun loadDetails(id: Int): LiveData<TMDbMovieItem> {
        currentCallback.addSource(TmdbRepository.getMovieDetails(id)) {
            _name.value = it.original_title
            _rating.value = it.vote_average.toString()
            _image.value = it.getPoster().toString()
        }
        return currentCallback
    }

    fun getMovieVideos(id: Int): LiveData<TMDbVideos> {
        movieVideosCallback.addSource(TmdbRepository.getMovieVideos(id)) {
            movieVideos.postValue(it)
        }
        return movieVideosCallback
    }

    fun getMovieVideos(): LiveData<TMDbVideos> {
        return movieVideos
    }
}