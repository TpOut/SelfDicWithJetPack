package com.example.selfdicwithjetpack.model.utils.storage

import com.tencent.mmkv.MMKV


/**
 * Created by TpOut on 2021/4/28.<br>
 * Email address: 416756910@qq.com<br>
 */
interface Storage {

    fun store(key: String, value: String)
    fun store(key: String, value: Boolean)
    fun store(key: String, value: Int)

    fun hasKey(key: String): Boolean

    fun queryString(key: String, defaultValue: String = ""): String
    fun queryBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun queryInt(key: String, defaultValue: Int = 0): Int

    fun delete(key: String)
}

// boolean, int, long, float, double, byte[]
// String, Set<String>, Parcelable
class MmkvStorage(diffId: String = "") : Storage {

    // even process Id
    var kv = if (diffId.isEmpty()) MMKV.defaultMMKV() else MMKV.mmkvWithID(diffId)

    override fun store(key: String, value: String) {
        kv?.encode(key, value)
    }

    override fun store(key: String, value: Boolean) {
        kv?.encode(key, value)
    }

    override fun store(key: String, value: Int) {
        kv?.encode(key, value)
    }

    override fun hasKey(key: String): Boolean {
        return kv?.containsKey(key) == true
    }

    override fun queryString(key: String, defaultValue: String): String {
        return kv?.decodeString(key, defaultValue) ?: defaultValue
    }

    override fun queryBoolean(key: String, defaultValue: Boolean): Boolean {
        return kv?.decodeBool(key, defaultValue) ?: defaultValue
    }

    override fun queryInt(key: String, defaultValue: Int): Int {
        return kv?.decodeInt(key, defaultValue) ?: defaultValue
    }

    override fun delete(key: String) {
        kv?.removeValueForKey(key)
    }
}