package com.example.selfdicwithjetpack.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
const val DISPLAY_FRAG_TAG = "DisplayFrag"

class DisplayFrag : Fragment() {

    private var mView: View? = null

    private var dicSpinner: Spinner? = null
    private var mSpinnerAdapter: ArrayAdapter<String>? = null
    private var tvSpinnerTip: TextView? = null

    private val mAdapter = DisplayAdapter()
    private val viewModel: DisplayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null != mView) {
            return mView
        }
        mView = inflater.inflate(R.layout.display_frag, container, false)
        afterViewCreated(mView!!)
        return mView
    }

    // 这种写法不能直接使用 fab、rv 来获取view
    private fun afterViewCreated(rootView: View) {
        dicSpinner = rootView.findViewById<Spinner>(R.id.s)
        tvSpinnerTip = rootView.findViewById<TextView>(R.id.tv_s_tip).apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_DisplayFrag_to_DicCreateFrag)
            }
        }

        val fab = rootView.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "SaveSuccess", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        fab.setOnLongClickListener {
            val actionDisplayFragToDetailFrag = DisplayFragDirections.actionDisplayFragToRandomFrag()
            findNavController().navigate(actionDisplayFragToDetailFrag.actionId)
            true
        }

        val tv = rootView.findViewById<TextView>(R.id.tv)

        val loadStateAdapter = mAdapter.withLoadStateFooter(ExampleLoadStateAdapter(mAdapter::retry))
//        mAdapter.withLoadStateHeaderAndFooter(
//            header = ExampleLoadStateAdapter(mAdapter::retry),
//            footer = ExampleLoadStateAdapter(mAdapter::retry)
//        )

        rootView.findViewById<RecyclerView>(R.id.rv).adapter = loadStateAdapter

        // Activities can use lifecycleScope directly, but Fragments should instead use
        // viewLifecycleOwner.lifecycleScope.
//        viewLifecycleOwner.lifecycleScope.launch {
//            mAdapter.loadStateFlow.collectLatest { loadStates ->
//                LogUtils.d(
//                    "loadStateFlow : "
//                            + "\n mediator ${loadStates.mediator}" +
//                            "\n source ${loadStates.source}"
//                )
//
//                if (loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading) {
//                    tv.text = "加载中..."
//                } else if (loadStates.refresh is LoadState.NotLoading && loadStates.append is LoadState.NotLoading) {
//                    if (loadStates.refresh.endOfPaginationReached || loadStates.append.endOfPaginationReached) {
//                        tv.text = "全部加载完毕"
//                    } else {
//                        tv.text = "加载完毕"
//                    }
//                } else if (loadStates.refresh is LoadState.Error || loadStates.append is LoadState.Error) {
//                    tv.text = "加载出错..."
//                }
//            }
//        }

        viewModel.dicList.observe(viewLifecycleOwner, Observer<List<DicEntity>> { list ->
            lifecycleScope.launch {
                var mapJob = async {
                    list.map { it.name }
                }
                refreshSpinner(mapJob.await())
                fetchData()
            }
        })
    }

    private fun refreshSpinner(dicList: List<String>) {
        LogUtils.d(DISPLAY_FRAG_TAG, "refreshSpinner ${dicList.size}")
        if (dicList.isNotEmpty()) {
            if (null == dicSpinner?.adapter) {
                mSpinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item, dicList.toTypedArray()
                )
                dicSpinner?.adapter = mSpinnerAdapter
            } else {
                mSpinnerAdapter?.addAll(dicList)
            }
        } else {
            tvSpinnerTip?.text = "点此创建词典"
        }
    }

    private fun fetchData() {
        lifecycleScope.launchWhenResumed {
            viewModel.fetchData(dicSpinner?.selectedItem as String?).collectLatest {
                LogUtils.d("fetchData - $it}")
                mAdapter.submitData(it)
            }
        }
    }
}