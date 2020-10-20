package com.example.selfdicwithjetpack.component.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.selfdicwithjetpack.model.data.WordBean

/**
 * Created by TpOut on 2020/10/15.<br>
 * Email address: 416756910@qq.com<br>
 *
 *
 */

//
//fun getDisplayDb(appContext: Context) {
//    val db = Room.databaseBuilder(
//        appContext,
//        DisplayDatabase::class.java, "yourena"
//    ).build()
//    // 多进程 enableMultiInstanceInvalidation
//}
//
//@Database(entities = [WordBean::class], version = 1)
//class DisplayDatabase : RoomDatabase() {
//
////    Room.databaseBuilder()/Room.inMemoryDatabaseBuilder()
//
//    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
//
//    }
//
//    override fun createInvalidationTracker(): InvalidationTracker {
//
//    }
//
//    override fun clearAllTables() {
//
//    }
//}