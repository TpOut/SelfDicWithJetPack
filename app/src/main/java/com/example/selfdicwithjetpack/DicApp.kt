package com.example.selfdicwithjetpack

import android.app.Application
import com.example.selfdicwithjetpack.component.data.Sp

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class DicApp  : Application(){


    override fun onCreate() {
        super.onCreate()

        Sp.init(this)
    }

}