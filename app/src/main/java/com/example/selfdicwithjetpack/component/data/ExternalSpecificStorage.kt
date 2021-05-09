package com.example.selfdicwithjetpack.component.data

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleCoroutineScope
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.model.utils.storage.SharedStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.File
import java.util.*

class ExternalSpecificStorage : SharedStorage {

    // adb shell sm set-virtual-disk true
    // mounted - 可读写；mounted_read_only - 只读
    fun checkState(): String {
        return Environment.getExternalStorageState()
    }

    fun getDir(context: Context): File {
        return ContextCompat.getExternalFilesDirs(context, null)[0]
    }

    // android 11 之后，不能在ExternalFilesDir 之外创建specific 目录
    fun getOrCreateDir(context: Context, albumName: String): File? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName)
        if (!file.mkdirs()) {
            LogUtils.e("屠龙宝刀", "Directory not created")
        }
        return file
    }

    fun accessFile(context: Context, fileType: String, filename: String): File {
        return File(context.getExternalFilesDir(fileType), filename)
    }

    fun createCacheFile(context: Context, filename: String): File{
        return File(context.externalCacheDir, filename)
    }

    fun deleteCacheFile(filename: File): Boolean{
        return filename.delete()
    }

    // 如果不知道多少值合适，则直接使用File 的api 进行编写，通过异常判断
    //
    // App needs 10 MB within internal storage.
    val NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * 10L;
    fun checkSpace(context: Context, scope: LifecycleCoroutineScope){
//        StorageStatsManager.getFreeBytes() / StorageStatsManager.getTotalBytes()

        val storageManager = context.getSystemService<StorageManager>()!!
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            scope.launch {
                withContext(Dispatchers.IO){
                    val appSpecificInternalDirUuid: UUID = storageManager.getUuidForPath(getDir(context))
                    val availableBytes: Long = storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
                    if (availableBytes >= NUM_BYTES_NEEDED_FOR_MY_APP) {
                        withTimeout(30_000){
                            storageManager.allocateBytes(appSpecificInternalDirUuid, NUM_BYTES_NEEDED_FOR_MY_APP)
                        }
                    } else {
                        val storageIntent = Intent().apply {
                            //ACTION_CLEAR_APP_CACHE.
                            action = ACTION_MANAGE_STORAGE
                        }
                    }
                }
            }
        }

    }




}