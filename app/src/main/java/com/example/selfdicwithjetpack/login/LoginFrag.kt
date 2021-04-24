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
import kotlinx.android.synthetic.main.login_frag.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginFrag : Fragment(), LoginHandler {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.handler = this
        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: LoginFragBinding) {
        lifecycleScope.launch {
            binding.userBean = viewModel.fetchUserData()
            if (!binding.userBean?.name.isNullOrEmpty()) {
                delay(3000)
                findNavController().navigate(R.id.action_LoginFrag_toDisplayFrag)
            }
        }
    }

    override fun onNextClick(v: View) {
        val name = et_name.text.toString()
        val birthday = et_birthday.text.toString()
        val city = et_city.text.toString()
        val street = et_street.text.toString()

        when {
            name.isEmpty() -> {
                ToastUtil.showShort(requireContext(), LoginConstant.LOGIN_NAME)
            }
            birthday.isEmpty() -> {
                ToastUtil.showShort(requireContext(), LoginConstant.LOGIN_BIRTHDAY)
            }
            city.isEmpty() -> {
                ToastUtil.showShort(requireContext(), LoginConstant.LOGIN_ADDRESS_CITY)
            }
            street.isEmpty() -> {
                ToastUtil.showShort(requireContext(), LoginConstant.LOGIN_ADDRESS_STREET)
            }
            else -> {
                lifecycleScope.launch {
                    if (viewModel.saveUserData(name, birthday, city, street)) {
                        findNavController().navigate(R.id.action_LoginFrag_toDisplayFrag)
                    } else {
                        ToastUtil.showShort(requireContext(), LoginConstant.LOGIN_ADDRESS_STREET)
                    }
                }
            }
        }
    }

    override fun onTempClick(v: View) {
        lifecycleScope.launch {
            binding.userBean = DEFAULT_USER_BEAN
            delay(3000)
            findNavController().navigate(R.id.action_LoginFrag_toDisplayFrag)
        }
    }
}