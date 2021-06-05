package com.example.selfdicwithjetpack.display

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navOptions
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.databinding.DisplayRvHeaderBinding
import com.example.selfdicwithjetpack.databinding.DisplayRvItemBinding

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
const val DISPLAY_ADAPTER_TAG = "DisplayAdapter"

class DisplayAdapter :
    PagingDataAdapter<DisplayUIModel, RecyclerView.ViewHolder>(DisplayDiffCallback()) {

    var inMultiQuickMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.display_rv_header -> DisplayHeaderViewHolder(
                DisplayRvHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.display_rv_item -> DisplayItemViewHolder(
                DisplayRvItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> DisplayHeaderViewHolder(
                DisplayRvHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is DisplayUIModel.DisplayHeaderModel -> R.layout.display_rv_header
        is DisplayUIModel.DisplayItemModel -> R.layout.display_rv_item
        else -> throw IllegalStateException("Unknown view")
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        LogUtils.d(DISPLAY_ADAPTER_TAG, "onBindViewHolder : $holderItem")
        if (holderItem is DisplayHeaderViewHolder) {

        } else if (holderItem is DisplayItemViewHolder) {
            holderItem.inMultiQuickMode = inMultiQuickMode
            holderItem.bind(item as DisplayUIModel.DisplayItemModel)
        }
    }

    class DisplayHeaderViewHolder(private val binding: DisplayRvHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    class DisplayItemViewHolder(private val binding: DisplayRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var inMultiQuickMode: Boolean = false

        init {
            binding.root.setOnClickListener { view ->
                val actionDisplayFragToDetailFrag =
                    DisplayFragDirections.actionDisplayFragToDetailFrag(
                        binding.item!!.src,
                        binding.item!!.dst,
                        binding.item!!.sentence
                    )
                view.findNavController().navigate(
                    actionDisplayFragToDetailFrag,
                    navOptions {
                        anim{
                            enter = R.anim.trans_x__300_0_duration_1000
                        }
                    }
                )
            }
        }

        fun bind(item: DisplayUIModel.DisplayItemModel) {
            // ViewCompat.setTransitionName(image, id)
            binding.apply {
                // 考虑优化
                this.root.setOnLongClickListener { dragView ->
                    if (inMultiQuickMode) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                            dragView.startDragAndDrop(
                                ClipData(
                                    ClipDescription(
                                        "列表信息",
                                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                    ),
                                    ClipData.Item(Intent().apply {
                                        putExtra("src", item.src)
                                        putExtra("dst", item.dst)
                                        putExtra("sentence", item.sentence)
                                    })
                                ),
                                View.DragShadowBuilder(dragView),
                                "local",
                                View.DRAG_FLAG_GLOBAL
                            )
                            return@setOnLongClickListener true
                        }
                        return@setOnLongClickListener false
                    } else {
                        return@setOnLongClickListener false
                    }
                }
                this.item = item
                executePendingBindings()
            }
        }
    }
}

// AsyncPagingDataDiffer
private class DisplayDiffCallback : DiffUtil.ItemCallback<DisplayUIModel>() {
    override fun areItemsTheSame(oldItem: DisplayUIModel, newItem: DisplayUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DisplayUIModel, newItem: DisplayUIModel): Boolean {
        LogUtils.d(
            DISPLAY_ADAPTER_TAG,
            "areContentsTheSame : ${oldItem.toString()} -- ${newItem.toString()}"
        )
        return oldItem.toString() == newItem.toString()

    }
}
