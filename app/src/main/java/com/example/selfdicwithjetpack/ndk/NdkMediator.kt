package com.example.selfdicwithjetpack.ndk

/**
 * Created by TpOut on 2020/10/27.<br>
 * Email address: 416756910@qq.com<br>
 */
class NdkMediator {
    external fun getNativeString(): String?

    external fun createNativeCrash()

    init {
        System.loadLibrary("jni-function")
    }
}