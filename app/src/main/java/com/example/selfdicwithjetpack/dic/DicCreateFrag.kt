package com.example.selfdicwithjetpack.dic

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.databinding.DicCreateFragBinding

/**
 * Created by TpOut on 2020/11/9.<br>
 * Email address: 416756910@qq.com<br>
 */
const val DIC_CREATE_FRAG_TAG = "DicCreateFrag"

class DicCreateFrag : Fragment() {

    private val viewModel: DicCreateViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DicCreateFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.handler = this
        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: DicCreateFragBinding) {
    }

    // 模拟器键盘需要按pc 的回车键才行
    fun onEditorActionDone(text: String): Boolean {
        if (text.isEmpty()) {
            return false
        }
        viewModel.insertDic(text)
        KeyboardUtils.hideSoftInput(requireActivity())
        findNavController().popBackStack()
        return true
    }

}