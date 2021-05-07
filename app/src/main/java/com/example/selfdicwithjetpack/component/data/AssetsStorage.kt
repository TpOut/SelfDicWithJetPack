package com.example.selfdicwithjetpack.component.data

import android.content.Context
import com.example.selfdicwithjetpack.model.utils.storage.FileStorage

class AssetsStorage : FileStorage{

    // 在install time 时，可以访问原始文件
    // R.raw
    fun readStream(context: Context, rawResId: Int){
        context.resources.openRawResource(rawResId).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }

}