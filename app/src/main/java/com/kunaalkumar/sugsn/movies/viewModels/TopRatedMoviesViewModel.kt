package com.kunaalkumar.sugsn.movies.viewModels

import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.RecyclerViewAdapter
import com.kunaalkumar.sugsn.imdb.ImdbRepository
import com.kunaalkumar.sugsn.omdb.OMDBRepository
import com.kunaalkumar.sugsn.util.ListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TopRatedMoviesViewModel : ViewModel(), VMWrapper {

    val TAG: String = "Sugsn@TopRatedMoviesViewModel"
    override val adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    private lateinit var itemQueue: Queue<ListItem>
    private val mDisposable = CompositeDisposable()
    private val topRatedMoviesList by lazy {
        ImdbRepository.getDetailedTopRatedMovies()
    }

    init {
        getTopMovies()
    }

    private fun getTopMovies() {
        mDisposable.add(topRatedMoviesList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { movieList ->
                itemQueue = LinkedList(movieList)
                loadNextTenItems()
            }
        )
    }

    // Removes the first ten elements from the queue and adds them to the adapter
    override fun loadNextTenItems() {
        for (i in 1..10) { // Pop first ten items from queue
            if (::itemQueue.isInitialized) {
                val item = itemQueue.remove()
                mDisposable.add( // Make call to OMDB API
                    OMDBRepository.getRottenRating(item.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe { response ->
                            item.rottenRating = response
                            adapter.updateItem(item)
                        }
                )
                adapter.addItem(item)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}

