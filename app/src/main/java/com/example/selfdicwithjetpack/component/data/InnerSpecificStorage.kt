package com.example.selfdicwithjetpack.component.data

import android.content.Context
import android.os.Build
import android.os.storage.StorageManager
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.selfdicwithjetpack.model.utils.storage.FileStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

/**
 * 一些方法只是为了标记
 * 不一定要使用
 */
class InnerSpecificStorage : FileStorage {

    //Environment.getDataDirectory()
    fun getDirectory(context: Context): File {
        return context.filesDir
    }

    fun getOrCreateDirectory(context: Context, dirName: String): File {
        return context.getDir(dirName, Context.MODE_PRIVATE)
    }

    fun getFilesInDirectory(context: Context): Array<String> {
        return context.fileList()
    }

    fun access(context: Context, filename: String): File {
        return File(context.filesDir, filename)
    }

    fun readStream(context: Context, filename: String) {
        context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }

    // 外部共享的话，FileProvider with the FLAG_GRANT_READ_URI_PERMISSION
    fun store(context: Context, filename: String, fileContent: String) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
    }

    fun getCacheDirectory(context: Context): File {
        return context.cacheDir
    }

    fun checkCacheDirectoryQuota(context: Context, scope: LifecycleCoroutineScope): Long {
        var quota: Long = -1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            scope.launch {
                var uuid: UUID? = null
                withContext(Dispatchers.IO) {
                    uuid = storageManager.getUuidForPath(getCacheDirectory(context))
                    uuid?.let {
                        quota = storageManager.getCacheQuotaBytes(it)
                    }
                }
            }
        }
        Log.d("屠龙宝刀", "限制为：$quota")
        return quota
    }

    fun createCacheFile(context: Context, filename: String): File {
        return File.createTempFile(filename, null, getCacheDirectory(context))
    }

    fun deleteCacheFile(cacheFile: File): Boolean{
        return cacheFile.delete()
    }

    fun deleteCacheFile(context: Context, cacheFile: String){
        context.deleteFile(cacheFile)
    }



}