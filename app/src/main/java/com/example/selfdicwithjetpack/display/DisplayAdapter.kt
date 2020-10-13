package com.example.selfdicwithjetpack.display

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfdicwithjetpack.databinding.DisplayRvItemBinding

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
class DisplayAdapter : PagingDataAdapter<DisplayBean, DisplayAdapter.DisplayViewHolder>(DisplayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayViewHolder {
        return DisplayViewHolder(
            DisplayRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DisplayViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }


    class DisplayViewHolder(private val binding: DisplayRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { view ->
                //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        fun bind(item: DisplayBean) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }
}

private class DisplayDiffCallback : DiffUtil.ItemCallback<DisplayBean>() {
    override fun areItemsTheSame(oldItem: DisplayBean, newItem: DisplayBean): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DisplayBean, newItem: DisplayBean): Boolean {
        return oldItem.src == newItem.src

    }
}
