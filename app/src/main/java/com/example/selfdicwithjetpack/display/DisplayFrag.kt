package com.example.selfdicwithjetpack.display

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.component.ui.BaseFrag
import com.example.selfdicwithjetpack.detail.DetailAct
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

    private var dicSpinner: Spinner? = null
    private var mSpinnerAdapter: ArrayAdapter<String>? = null
    private var tvSpinnerTip: TextView? = null
    private var btnMultiWindowDetail: Button? = null
    private var rv: RecyclerView? = null
    private var etQuery: EditText? = null
    private var etSentence: EditText? = null
    private var ivIcon: ImageView? = null

    private var waitScrollToTop = false

    private val mAdapter = DisplayAdapter()
    private val viewModel: DisplayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // 关联appbar 中menu
        // 如果不加这个，动画会因为初始化等操作出现卡顿
        // 对应 startPostponedEnterTransition
        postponeEnterTransition()
//        enterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.fade)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.image)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null != view) {
            return view
        }
        val v = inflater.inflate(R.layout.display_frag, container, false)
        afterViewCreated(v)
        lifecycleRebind()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.printDicName()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.display_menu, menu)
    }

    // 主动刷新 requireActivity().invalidateOptionsMenu()
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
//        val item = menu.findItem(R.id.action_done)
//        item.isVisible = isEditing
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_display -> {
                // navigate to settings screen
                ToastUtils.showShort("你好，展示页面")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        LogUtils.d(DISPLAY_FRAG_TAG, "onMultiWindowModeChanged : $isInPictureInPictureMode")
    }

    // false 的时候，是在onConfigurationChanged 之后一段时间调。小米mix2 手机
    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
        LogUtils.d(DISPLAY_FRAG_TAG, "onMultiWindowModeChanged : $isInMultiWindowMode")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.d(DISPLAY_FRAG_TAG, "onConfigurationChanged : $newConfig")
    }

    // 目前navigation 导航使用replace ，会重走onCreateView/onDestroyView.
    private fun lifecycleRebind() {
        lifecycleScope.launch {
//            DicListFrag().show(childFragmentManager, "bottomSheet")
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
        ivIcon = rootView.findViewById(R.id.icon)
        dicSpinner = rootView.findViewById<Spinner>(R.id.s)
        tvSpinnerTip = rootView.findViewById<TextView>(R.id.tv_s_tip)
        btnMultiWindowDetail = rootView.findViewById(R.id.btn)
        btnMultiWindowDetail?.setOnClickListener { btn ->
            startActivity(Intent().apply {
                setClass(requireContext(), DetailAct::class.java)
//                component = ComponentName("com.example.temppractice","com.example.temppractice.MainActivity")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!requireActivity().isInMultiWindowMode) {
                        ToastUtils.showShort("请先进入分屏模式")
                        return@setOnClickListener
                    }
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT
                } else {
                    ToastUtils.showShort("需要支持分配的设备")
                }
            })
            mAdapter.inMultiQuickMode = true
            mAdapter.notifyDataSetChanged()
        }
        rv = rootView.findViewById<RecyclerView>(R.id.rv)
        etQuery = rootView.findViewById(R.id.et_query)
        etQuery?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val items = mAdapter.snapshot().items
                run loop@{
                    items.forEachIndexed { index, displayUIModel ->
                        if (displayUIModel !is DisplayUIModel.DisplayItemModel) {
                            return@forEachIndexed
                        }
                        if (displayUIModel.src.contains(s.toString())) {
                            rv?.scrollToPosition(index)
                            return@loop
                        }
                    }
                }
            }
        })
        etSentence = rootView.findViewById(R.id.et_sentence)

        val fab = rootView.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            viewLifecycleOwner.lifecycleScope.launch {
                val src = etQuery?.text.toString()
                if (src.isEmpty()) {
                    return@launch
                }
                val items = mAdapter.snapshot().items
                val index = items.indexOf(DisplayUIModel.DisplayItemModel(src, "", ""))
                if (index != -1) {
                    Snackbar.make(view, "已经存在了哦", Snackbar.LENGTH_LONG).show()
                    return@launch
                }
                if (viewModel.queryWord(src, etSentence?.text.toString())) {
                    waitScrollToTop = true
                    Snackbar.make(view, "保存成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                } else {
                    Snackbar.make(view, "保存失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
        }
        fab.setOnLongClickListener {
            val actionDisplayFragToDetailFrag =
                DisplayFragDirections.actionDisplayFragToRandomFrag()
            findNavController().navigate(
                actionDisplayFragToDetailFrag.actionId,
                null,
                null,
                FragmentNavigatorExtras(ivIcon!! to "icon")
            )
            true
        }

        val tv = rootView.findViewById<TextView>(R.id.tv)

        val loadStateAdapter =
            mAdapter.withLoadStateFooter(ExampleLoadStateAdapter(mAdapter::retry))
//        mAdapter.withLoadStateHeaderAndFooter(
//            header = ExampleLoadStateAdapter(mAdapter::retry),
//            footer = ExampleLoadStateAdapter(mAdapter::retry)
//        )

        rv?.adapter = loadStateAdapter

        // Activities can use lifecycleScope directly, but Fragments should instead use
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
            viewModel.setDicName(dicList.lastOrNull())

            tvSpinnerTip?.text = "⬅️ 当前词典"
            tvSpinnerTip?.setOnClickListener { }
            fetchData()
        } else {
            tvSpinnerTip?.text = "请先点此创建词典"
            tvSpinnerTip?.setOnClickListener {
                findNavController().navigate(R.id.action_DisplayFrag_to_DicCreateFrag)
            }
        }
        startPostponedEnterTransition()
    }

    //todo 词典可以变换之后，需要处理取消观察和重新绑定观察的逻辑
    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.fetchData(dicSpinner?.selectedItem as String?).collectLatest {
//                LogUtils.d("fetchData - $it}")
//                mAdapter.submitData(it)
//            }
            viewModel.fetchMediatorData().collectLatest {
                LogUtils.d("submitData - $it}")
                mAdapter.submitData(it)
//                (view?.parent as? ViewGroup)?.doOnPreDraw {
//                    startPostponedEnterTransition()
//                }
                if (waitScrollToTop) {
                    waitScrollToTop = false
                    rv?.scrollToPosition(0)
                }
                LogUtils.d("submitData - ${mAdapter.itemCount}}")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}