package com.kunaalkumar.sugsn.movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import kotlinx.android.synthetic.main.fragment_recycler_view.*

/**
 * A simple [Fragment] subclass.
 */
class TopRatedMoviesFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(TopRatedMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = viewModel.adapter
        recycler_view.setItemViewCacheSize(20)
        recycler_view.hasFixedSize()

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this.adapter
        }

        // Scroll additional items once user reaches end of list
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextTenItems()
                }
            }
        })

    }

}