package com.kunaalkumar.suggsn.ui.Results

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.taste_dive.TDItem

class ResultsAdapter(val dataSet: List<TDItem>) : RecyclerView.Adapter<ResultViewHolder>() {

    private val TAG: String = "ResultsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: Binding $position")
        holder.bindView(dataSet[position])
    }
}