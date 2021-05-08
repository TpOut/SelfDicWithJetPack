package com.example.selfdicwithjetpack.model.utils.storage


/**
 * Created by TpOut on 2021/4/28.<br>
 * Email address: 416756910@qq.com<br>
 */
interface FileStorage{

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