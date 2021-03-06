package com.kunaalkumar.sugsn.movies


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.results_components.TmdbResultsAdapter
import com.kunaalkumar.sugsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.sugsn.view_model.MoviesViewModel
import kotlinx.android.synthetic.main.activity_search.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TopRated(val viewModel: MoviesViewModel) : Fragment() {

    val TAG: String = "Sugssn@TopRated"

    private lateinit var viewAdapterTmdb: TmdbResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragments_recylcer_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()

        viewModel.getMovies(MoviesViewModel.TOP_RATED).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewAdapterTmdb.addResults(ArrayList(it.results))
            }
        })
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(context, 2)
        viewAdapterTmdb = TmdbResultsAdapter(MOVIE_MEDIA_TYPE)
        recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            layoutManager = viewManager
            adapter = viewAdapterTmdb
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.nextPage(MoviesViewModel.TOP_RATED)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
        recycler_view.layoutManager = null
    }
}
