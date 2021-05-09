package com.example.selfdicwithjetpack.model.utils.storage

import android.os.Environment


/**
 * Created by TpOut on 2021/4/28.<br>
 * Email address: 416756910@qq.com<br>
 */
interface SharedStorage{
    companion object{

        // MANAGE_EXTERNAL_STORAGE 需求权限
        // ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION，跳转intent
        fun canManageAll(): Boolean{
            return Environment.isExternalStorageManager()
        }
    }

}

interface KvStorage {

    fun store(key: String, value: String)
    fun store(key: String, value: Boolean)
    fun store(key: String, value: Int)

    fun hasKey(key: String): Boolean

    fun queryString(key: String, defaultValue: String = ""): String
    fun queryBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun queryInt(key: String, defaultValue: Int = 0): Int

    fun delete(key: String)
}