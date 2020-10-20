package com.example.selfdicwithjetpack.component.debug

import android.content.Context
import android.widget.Toast

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class ToastUtil {

    companion object{
        fun showShort(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}