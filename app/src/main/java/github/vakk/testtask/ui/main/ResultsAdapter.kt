package github.vakk.testtask.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import github.vakk.testtask.R
import github.vakk.testtask.common.Type
import github.vakk.testtask.model.dto.SearchResultItem
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
        private val progressBar: ProgressBar = itemView.findViewById(R.id.pbProgress)

        val UNKNOWN_PROGRESS = 0
        val DOWNLOADING = 1
        val PROCESSING = 2
        val FOUND = 3
        val NOT_FOUND = 3
        val ERROR = 3
        val MAX = 3

        init {
            progressBar.max = MAX
        }

        fun bind(item: SearchResultItem) {
            url.text = item.urlName
            totalFound.text = itemView.context.getString(R.string.found_words, item.searchStatus.wordsFound)
            bindStatus(item)
        }

        private fun bindStatus(item: SearchResultItem) {
            when (item.searchStatus.status) {
                Type.ItemProcessStatus.NOT_FOUND -> {
                    progressBar.isIndeterminate = false
                    progressBar.progress = NOT_FOUND
                    totalFound.text = "Not found..."
                }
                Type.ItemProcessStatus.DOWNLOADING -> {
                    progressBar.isIndeterminate = false
                    progressBar.progress = DOWNLOADING
                    totalFound.text = "Downloading..."
                }
                Type.ItemProcessStatus.PROCESSING -> {
                    progressBar.isIndeterminate = false
                    progressBar.progress = PROCESSING
                    totalFound.text = "Processing..."
                }
                Type.ItemProcessStatus.FOUND -> {
                    progressBar.isIndeterminate = false
                    progressBar.progress = FOUND
                    totalFound.text = itemView.context.getString(R.string.found_words, item.searchStatus.wordsFound)
                }
                Type.ItemProcessStatus.ERROR -> {
                    progressBar.isIndeterminate = false
                    progressBar.progress = ERROR
                    totalFound.text = "Error:${item.error?.message}"
                }
                Type.ItemProcessStatus.UNKNOWN -> {
                    progressBar.isIndeterminate = true
                    totalFound.text = "Waiting..."
                }
            }
        }


    }
}