package com.example.selfdicwithjetpack.component.data

import com.example.selfdicwithjetpack.model.utils.storage.KvStorage
import com.tencent.mmkv.MMKV

// boolean, int, long, float, double, byte[]
// String, Set<String>, Parcelable
class MmkvStorage(diffId: String = "") : KvStorage {

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