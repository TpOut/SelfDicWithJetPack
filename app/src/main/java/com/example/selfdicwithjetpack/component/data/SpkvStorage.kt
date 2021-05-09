package com.example.selfdicwithjetpack.component.data

import android.app.Activity
import android.content.Context
import com.example.selfdicwithjetpack.model.utils.storage.KvStorage

//
class SpkvStorage : KvStorage {

    // 都是线程安全的
    // EncryptedSharedPreferences
    fun init(context: Activity) {
        val sharedPref = context.getSharedPreferences("global", Context.MODE_PRIVATE)
        // activity 限定
//        val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
    }

    override fun store(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun store(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun store(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override fun hasKey(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun queryString(key: String, defaultValue: String): String {
        TODO("Not yet implemented")
    }

    override fun queryBoolean(key: String, defaultValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun queryInt(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }

    override fun delete(key: String) {
        TODO("Not yet implemented")
    }
}