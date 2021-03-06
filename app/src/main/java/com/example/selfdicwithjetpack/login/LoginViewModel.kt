package com.example.selfdicwithjetpack.login

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.component.data.MmkvStorage
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.model.user.AddressBean
import com.example.selfdicwithjetpack.model.user.UserBean
import com.example.selfdicwithjetpack.model.user.UserEntity

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */

const val LOGIN_VIEW_MODEL_TAG = "LoginViewModel"
val DEFAULT_USER_BEAN = UserBean("Tpout","19920513", AddressBean("WenZhou", "WenHua"))

class LoginViewModel : ViewModel() {

    val storage = MmkvStorage()

    suspend fun fetchUserData(): UserBean? {
        var userBean: UserBean? = null
        var userId = storage.queryInt(SP_KEY_LOGIN_USER_ID)
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, "查询登录用户id 为：${userId}")
        if (userId != 0) {
            userBean = AppDb.appDb.withTransaction {
                AppDb.appDb.userDao().getUserInfo(userId)
            }
            LogUtils.d(LOGIN_VIEW_MODEL_TAG, "查询登录用户信息 为：${userBean}")
        }
        return userBean
    }

    suspend fun saveUserData(name: String, birthday: String, city: String, street: String): Boolean {
        var currentUserInfo: UserEntity? = null
        val userDao = AppDb.appDb.userDao()
        val rowId = AppDb.appDb.withTransaction {
            userDao.insertUser(
                UserEntity(name = name, birthday = birthday, address = AddressBean(city, street))
            )
        }
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, "插入用户到第${rowId}行")
        currentUserInfo = AppDb.appDb.withTransaction {
            userDao.getCurrentUserInfo()
        }?.last()
        LogUtils.d(LOGIN_VIEW_MODEL_TAG, currentUserInfo)

        storage.store(SP_KEY_LOGIN_USER_ID, currentUserInfo?.id.toString().toInt())
        return currentUserInfo != null
    }
}