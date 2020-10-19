package com.example.selfdicwithjetpack.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
class LoginFrag: Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            //query sql

        }
    }

    override fun onResume() {
        super.onResume()
    }

}