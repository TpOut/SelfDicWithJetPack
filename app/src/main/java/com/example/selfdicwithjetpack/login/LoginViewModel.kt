package com.example.selfdicwithjetpack.login

import androidx.lifecycle.ViewModel
import com.example.selfdicwithjetpack.component.data.Sp
import com.example.selfdicwithjetpack.model.user.UserBean

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginViewModel : ViewModel() {

    fun fetchUserData(): UserBean? {
        Sp.queryName()?.let {
            return UserBean(it)
        }
        return null
    }

    fun saveUserData(name: String) {
        Sp.saveNme(name)
    }
}