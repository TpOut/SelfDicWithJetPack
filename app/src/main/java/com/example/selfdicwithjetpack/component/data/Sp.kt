package com.example.selfdicwithjetpack.component.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.selfdicwithjetpack.login.SP_KEY_LOGIN_USER_ID

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */

object Sp {

    lateinit var sharedPreferences: SharedPreferences

    fun init(appContext: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    fun saveUserId(id: Int) {
        sharedPreferences.edit {
            putInt(SP_KEY_LOGIN_USER_ID, id)
        }
    }

    fun queryUserId(): Int {
        return sharedPreferences.getInt(SP_KEY_LOGIN_USER_ID, 0);
    }

}