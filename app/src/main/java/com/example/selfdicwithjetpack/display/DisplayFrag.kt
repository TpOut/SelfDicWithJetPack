package com.example.selfdicwithjetpack.display

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DisplayFrag : Fragment() {

    private var mView: View? = null

    private var dicSpinner: Spinner? = null

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

        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launchWhenResumed {
            val dicListJob = async(Dispatchers.IO) {
                LogUtils.d("fetch dic list: start")
                val fetchDicList = viewModel.fetchDicList()
                LogUtils.d("fetch dic list: end ${fetchDicList.size}")
                fetchDicList
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dicListJob.await().toTypedArray())
            dicSpinner?.adapter = adapter

            viewModel.fetchData(dicSpinner?.selectedItem as String).collectLatest {
                LogUtils.d("fetchData - $it}")
                mAdapter.submitData(it)
            }
        }
    }
}