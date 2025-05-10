package com.khalil.paymobtask.ui.home.adabters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khalil.paymobtask.databinding.ItemLoadStateBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }
}

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding, // You need to create this layout
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        binding.retryButton.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        binding.errorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
            return LoadStateViewHolder(binding, retry)
        }
    }
}
