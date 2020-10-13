package com.example.selfdicwithjetpack.display

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.log.LogUtil
import kotlinx.android.synthetic.main.display_frag.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DisplayFrag : Fragment() {

    private val adapter = DisplayAdapter()
    private val viewModel: DisplayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv.adapter = adapter

        fetchData()
    }

    private fun fetchData() {
        LogUtil.d("fetchData")
        lifecycleScope.launch {
            LogUtil.d("launch - fetchData")
            viewModel.fetchData().collectLatest{
                LogUtil.d("launch - collectLatest")
                adapter.submitData(it)
            }
        }
    }
}