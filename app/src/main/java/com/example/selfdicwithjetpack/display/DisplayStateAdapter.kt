package com.example.selfdicwithjetpack.display

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.databinding.DisplayRvLoadStateItemBinding

/**
 * Created by TpOut on 2020/10/28.<br>
 * Email address: 416756910@qq.com<br>
 */
// Adapter that displays a loading spinner when
// state = LoadState.Loading, and an error message and retry
// button when state is LoadState.Error.
class ExampleLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.display_rv_load_state_item, parent, false)
) {
    private val binding = DisplayRvLoadStateItemBinding.bind(itemView)

    private val progressBar: ProgressBar = binding.pb
    private val errorMsg: TextView = binding.tvMsg
    private val retry = binding.tvRetry.also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        LogUtils.d("loadStateFlow - $loadState")
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}