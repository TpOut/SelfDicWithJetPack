package com.example.selfdicwithjetpack.component.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils

/**
 * Created by TpOut on 2020/11/10.<br>
 * Email address: 416756910@qq.com<br>
 */
const val BASE_FRAG_TAG = "BaseFrag"

open class BaseFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(BASE_FRAG_TAG, "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d(BASE_FRAG_TAG, "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(BASE_FRAG_TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(BASE_FRAG_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(BASE_FRAG_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(BASE_FRAG_TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.d(BASE_FRAG_TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(BASE_FRAG_TAG, "onDestroy")
    }
}