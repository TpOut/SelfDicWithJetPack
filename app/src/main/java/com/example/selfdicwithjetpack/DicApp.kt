package com.example.selfdicwithjetpack

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.component.data.Sp
import com.example.selfdicwithjetpack.component.debug.log.LogUtil

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class DicApp  : Application(){


    override fun onCreate() {
        super.onCreate()

        LogUtil
        Sp.init(this)
    }

}