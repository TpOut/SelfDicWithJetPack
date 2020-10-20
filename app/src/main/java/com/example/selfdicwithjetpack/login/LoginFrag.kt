package com.example.selfdicwithjetpack.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.selfdicwithjetpack.databinding.LoginFragBinding
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginFrag : Fragment() {

    private var viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = LoginFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        afterViewCreated(binding)
        return binding.root
    }

    fun afterViewCreated(binding: LoginFragBinding) {
        lifecycleScope.launch {
            var userBean = viewModel.fetchUserData()
            binding.bean = userBean
        }
    }

    fun
}