package com.example.selfdicwithjetpack.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.component.debug.log.LogUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.display_frag.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DisplayFrag : Fragment() {

    private var mView: View? = null

    // ExampleLoadStateAdapter
    //.withLoadStateHeaderAndFooter(
    //    header = ExampleLoadStateAdapter(adapter::retry),
    //    footer = ExampleLoadStateAdapter(adapter::retry)
    //  )
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

        rootView.findViewById<RecyclerView>(R.id.rv).adapter = mAdapter
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launchWhenResumed {
            viewModel.fetchData().collectLatest {
                LogUtil.d("fetchData - $it")
                mAdapter.submitData(it)
            }
            mAdapter.loadStateFlow.collectLatest { loadStates ->
                LogUtil.d("loadStateFlow - ${loadStates.refresh}")
                when (loadStates.refresh) {
                    is LoadState.NotLoading -> tv.text = if (loadStates.refresh.endOfPaginationReached) "加载完毕" else "全部加载完毕"
                    is LoadState.Loading -> tv.text = "加载中..."
                    is LoadState.Error -> tv.text = "加载出错..."
                }
            }
        }
    }
}