package com.example.selfdicwithjetpack.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.component.data.MmkvStorage
import com.example.selfdicwithjetpack.component.debug.ToastUtil
import com.example.selfdicwithjetpack.component.ui.BaseFrag
import com.example.selfdicwithjetpack.databinding.LoginFragBinding
import com.example.selfdicwithjetpack.general.PrivacyDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginFrag : BaseFrag(), LoginHandler {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragBinding
    val storage = MmkvStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.fade)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        if (!storage.hasKey(PrivacyDialog.KEY_STORAGE_PRIVATE_DIALOG_SHOW)) {
            // 这种方式，除了统一，比起直接写，好像没啥好处吧
            findNavController().navigate(
                R.id.action_LoginFrag_showPrivacyDialog
            )
        }
        // todo 没同意隐私条款之前，不做逻辑
        binding.handler = this
        binding.mlRoot.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                p0?.enableTransition(R.id.trans_btn_guest, false)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }
        })
        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: LoginFragBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.userBean = viewModel.fetchUserData()
            if (!binding.userBean?.name.isNullOrEmpty()) {
                delay(3000)
                findNavController().navigate(
                    R.id.action_LoginFrag_toDisplayFrag,
                    null,
                    null,
                    FragmentNavigatorExtras(binding.icon to "icon")
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onNextClick(v: View) {
        val name = binding.etName.text.toString()
        val birthday = binding.etBirthday.text.toString()
        val city = binding.etCity.text.toString()
        val street = binding.etStreet.text.toString()

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

    override fun onGuestClick(v: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.userBean = DEFAULT_USER_BEAN
            delay(3000)
            findNavController().navigate(
                R.id.action_LoginFrag_toDisplayFrag,
                null,
                null,
                FragmentNavigatorExtras(binding.icon to "icon")
            )
        }
    }
}