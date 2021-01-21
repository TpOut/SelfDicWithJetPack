package com.example.selfdicwithjetpack.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.component.ui.BaseFrag
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
const val DISPLAY_FRAG_TAG = "DisplayFrag"

class DisplayFrag : BaseFrag() {

    private var mView: View? = null

    private var dicSpinner: Spinner? = null
    private var mSpinnerAdapter: ArrayAdapter<String>? = null
    private var tvSpinnerTip: TextView? = null
    private var etQuery: EditText? = null
    private var etSentence: EditText? = null

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
        lifecycleRebind()
        return mView
    }

    // 目前navigation 导航使用replace ，会重走onCreateView/onDestroyView.
    private fun lifecycleRebind() {
        lifecycleScope.launch {
            viewModel.getDicListDeferred()?.distinctUntilChanged()?.collectLatest { list ->
                LogUtils.d(DISPLAY_FRAG_TAG, "dicList observe ${list.size}")
                var mapJob = async(Dispatchers.Default) {
                    list.map {
                        LogUtils.d(DISPLAY_FRAG_TAG, "dicList map ${it.name}")
                        it.name
                    }
                }
                refreshSpinner(mapJob.await())
            }
        }
    }

    // 这种写法不能直接使用 fab、rv 来获取view
    private fun afterViewCreated(rootView: View) {
        dicSpinner = rootView.findViewById<Spinner>(R.id.s)
        tvSpinnerTip = rootView.findViewById<TextView>(R.id.tv_s_tip)
        etQuery = rootView.findViewById(R.id.et_query)
        etSentence = rootView.findViewById(R.id.et_sentence)

        val fab = rootView.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            lifecycleScope.launch {
                if(viewModel.queryWord(etQuery?.text.toString(), etSentence?.text.toString())){
                    Snackbar.make(view, "SaveSuccess", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
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
    }

    private fun refreshSpinner(dicList: List<String>) {
        LogUtils.d(DISPLAY_FRAG_TAG, "refreshSpinner ${dicList.size}")
        if (dicList.isNotEmpty()) {
            if (null == dicSpinner?.adapter) {
                mSpinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item, dicList.toMutableList()
                )
                dicSpinner?.adapter = mSpinnerAdapter
            } else {
                mSpinnerAdapter?.clear()
                mSpinnerAdapter?.addAll(dicList)
            }
            dicSpinner?.setSelection(dicList.size - 1)
            tvSpinnerTip?.text = "⬅️ 当前词典"
            tvSpinnerTip?.setOnClickListener { }
            fetchData()
        } else {
            tvSpinnerTip?.text = "请先点此创建词典"
            tvSpinnerTip?.setOnClickListener {
                findNavController().navigate(R.id.action_DisplayFrag_to_DicCreateFrag)
            }
        }
    }

    //todo 词典可以变换之后，需要处理取消观察和重新绑定观察的逻辑
    private fun fetchData() {
        lifecycleScope.launchWhenResumed {
//            viewModel.fetchData(dicSpinner?.selectedItem as String?).collectLatest {
//                LogUtils.d("fetchData - $it}")
//                mAdapter.submitData(it)
//            }
            viewModel.fetchMediatorData().collectLatest {
                LogUtils.d("submitData - $it}")
                mAdapter.submitData(it)
                LogUtils.d("submitData - ${mAdapter.itemCount}}")
            }
        }
    }
}