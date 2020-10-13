package com.example.selfdicwithjetpack.log

import android.util.Log

/**
 * Created by TpOut on 2020/10/13.<br>
 * Email address: 416756910@qq.com<br>
 */
const val GLOBAL_TAG = "屠龙宝刀"

object LogUtil {

    fun d(msg: String) {
        Log.d(GLOBAL_TAG, msg)
    }

}