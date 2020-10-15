package com.example.selfdicwithjetpack.db

import android.content.Context
import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Room

/**
 * Created by TpOut on 2020/10/15.<br>
 * Email address: 416756910@qq.com<br>
 */

//
fun getDisplayDb(appContext: Context) {
    val db = Room.databaseBuilder(
        appContext,
        DisplayDatabase::class.java, "database-name"
    ).build()
    // 多进程 enableMultiInstanceInvalidation
}

// 对1:1 关联的表进行直接查询
data class UserAndLibrary(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val word: Word
)