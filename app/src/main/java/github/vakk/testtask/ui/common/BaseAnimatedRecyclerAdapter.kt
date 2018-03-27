package github.vakk.testtask.ui.common

import android.support.v7.widget.RecyclerView
import java.util.*

/**
 * Created by Valery Kotsulym on 8/31/17.
 */


abstract class BaseAnimatedRecyclerAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    private val items = Collections.synchronizedList(ArrayList<T>())

    protected fun getItem(position: Int): T {
        return items[position]
    }

    fun remove(item: T) {
        val itemPosition = itemPosition(item)
        if (itemPosition != -1) {
            items.remove(item)
            notifyItemRemoved(itemPosition)
        }
    }

    fun update(item: T) {
        val itemPosition = itemPosition(item)
        if (itemPosition != -1) {
            val listItem = items[itemPosition]
            if (!isTheSameContent(item, listItem)) {
                items[itemPosition] = item
                notifyItemChanged(itemPosition)
            }
        }
    }

    fun clearList() {
        val size = itemCount
        items.clear()
        notifyDataSetChanged()
    }

    fun changeDataSet(items: List<T>) {
        clearList()
        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    protected operator fun contains(item: T): Boolean {
        for (listItem in items) {
            if (isTheSameItems(item, listItem)) {
                return true
            }
        }
        return false
    }

    /**
     * Search item position in current list.
     *
     * @param item which should be found.
     * @return position of required item or -1 if nothing was found.
     */
    protected fun itemPosition(item: T): Int {
        items.indices.forEach { i ->
            val listItem = items[i]
            if (isTheSameItems(listItem, item)) {
                return i
            }
        }
        return -1
    }

    /**
     * Describe some unique logic for identify items.
     *
     * @param first  item which should be checked.
     * @param second item which should be checked.
     * @return true if it's a same items.
     */
    protected abstract fun isTheSameItems(first: T, second: T): Boolean

    /**
     * Describe some unique logic for compare items.
     *
     * @param first  item which should be checked.
     * @param second item which should be checked.
     * @return true if item is not changed.
     */
    protected abstract fun isTheSameContent(first: T, second: T): Boolean

    fun addItem(item: T) {
        if (!contains(item)) {
            items.add(item)
            val i = itemPosition(item)
            notifyItemInserted(i)
        } else {
            val index = itemPosition(item)
            if (index >= 0) {
                notifyItemChanged(index)
            }
        }
    }

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
