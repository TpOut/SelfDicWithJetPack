package com.example.selfdicwithjetpack.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.component.debug.ToastUtil
import com.example.selfdicwithjetpack.databinding.LoginFragBinding
import kotlinx.android.synthetic.main.detail_frag.*
import kotlinx.android.synthetic.main.login_frag.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginFrag : Fragment(), LoginHandler {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = LoginFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.handler = this
        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: LoginFragBinding) {
        lifecycleScope.launch {
            var userBean = viewModel.fetchUserData()
            binding.userBean = userBean
            if (!userBean.name.isNullOrEmpty()) {
                delay(3000)
                findNavController().navigate(R.id.action_LoginFrag_toDisplayFrag)
            }
        }
    }

    override fun onNextClick(v: View) {
        val name = et_name.text.toString()
        if (name.isEmpty()) {
            ToastUtil.showShort(requireContext(), LoginConstant.LOGIN)
        } else {
            viewModel.saveUserData(name)
            findNavController().navigate(R.id.action_LoginFrag_toDisplayFrag)
        }
    }
}