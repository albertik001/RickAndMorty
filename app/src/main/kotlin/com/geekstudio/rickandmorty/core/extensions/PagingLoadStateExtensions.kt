package com.geekstudio.rickandmorty.core.extensions

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.bindUIToLoadState(
    recyclerView: RecyclerView,
    progressBar: ProgressBar,
    view: View? = null,
    listener: ((CombinedLoadStates) -> Unit)? = null
) {
    addLoadStateListener { loadStates ->
        recyclerView.isVisible = loadStates.refresh is LoadState.NotLoading
        progressBar.isVisible = loadStates.refresh is LoadState.Loading
        view?.isVisible = loadStates.refresh !is LoadState.Loading
        if (recyclerView.isVisible) {
            view?.isVisible = false
        }
        listener?.invoke(loadStates)
    }
}