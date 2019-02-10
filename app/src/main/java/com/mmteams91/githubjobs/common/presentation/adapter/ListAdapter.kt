package com.mmteams91.githubjobs.common.presentation.adapter

import android.support.v7.widget.RecyclerView

abstract class ListAdapter<I, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    var items: MutableList<I> = mutableListOf()

    @Synchronized
    fun add(item: I) = items.add(item).also {
        notifyItemInserted(items.size - 1)
    }

    @Synchronized
    fun addAll(itemsToAdd: Collection<I>): Boolean {
        val first = itemCount
        val isAdded = items.addAll(itemsToAdd)
        if (isAdded) {
            notifyItemRangeInserted(first, itemsToAdd.size)
        }
        return isAdded
    }

    override fun getItemCount(): Int {
        return items.size
    }
}