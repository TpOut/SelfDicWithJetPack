package com.example.selfdicwithjetpack

import android.app.Application
import android.os.StrictMode
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class DicApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setStrictMode()
        val rootDir = MMKV.initialize(this)
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            LogUtils.d("AppUncaught", e.printStackTrace())
        }
    }

    private fun setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build()
            );
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            );
        }
    }

}