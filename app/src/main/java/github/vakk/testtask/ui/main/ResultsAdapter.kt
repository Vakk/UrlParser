package github.vakk.testtask.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import github.vakk.testtask.R
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.ui.common.BaseAnimatedRecyclerAdapter
import github.vakk.testtask.utils.extensions.layoutInflater

/**
 * Created by Valery Kotsulym on 3/27/18.
 */
class ResultsAdapter : BaseAnimatedRecyclerAdapter<SearchResultItem, ResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.layoutInflater().inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun isTheSameItems(first: SearchResultItem, second: SearchResultItem): Boolean = first == second

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun isTheSameContent(first: SearchResultItem, second: SearchResultItem): Boolean = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val url: TextView = itemView.findViewById(R.id.tvUrl)
        private val totalFound: TextView = itemView.findViewById(R.id.tvTotal)

        fun bind(item: SearchResultItem) {
            url.text = item.urlName
            totalFound.text = itemView.context.getString(R.string.found_words,
                    item.searchStatus.wordsFound)
        }
    }
}