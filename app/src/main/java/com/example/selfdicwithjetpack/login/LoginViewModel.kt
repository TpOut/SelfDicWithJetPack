package com.example.selfdicwithjetpack.login

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.component.data.Sp
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.model.user.AddressBean
import com.example.selfdicwithjetpack.model.user.UserBean
import com.example.selfdicwithjetpack.model.user.UserEntity

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */

const val LOGIN_VIEW_MODEL_TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {

    suspend fun fetchUserData(): UserBean? {
        var userBean: UserBean? = null
        var userId = Sp.queryUserId()
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, "查询登录用户id 为：${userId}")
        if (!userId.isNullOrEmpty()) {
            userBean = AppDb.appDb.userDao().getUserInfo(userId)
            LogUtils.d(LOGIN_VIEW_MODEL_TAG, "查询登录用户信息 为：${userBean}")
        }
        return userBean
    }

    suspend fun saveUserData(name: String, birthday: String, city: String, street: String): Boolean {
        var currentUserInfo: UserEntity? = null
        val userDao = AppDb.appDb.userDao()
        val rowId = userDao.insertUser(
            UserEntity(name = name, birthday = birthday, address = AddressBean(city, street))
        )
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, "插入用户到第${rowId}行")
        currentUserInfo = userDao.getCurrentUserInfo()?.last()
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, currentUserInfo)

        Sp.saveUserId(currentUserInfo?.id.toString())
        return currentUserInfo != null
    }
}