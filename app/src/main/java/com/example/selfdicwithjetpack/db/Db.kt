package com.example.selfdicwithjetpack.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

/**
 * Created by TpOut on 2020/10/15.<br>
 * Email address: 416756910@qq.com<br>
 *
 *
 */

//
fun getDisplayDb(appContext: Context) {
    val db = Room.databaseBuilder(
        appContext,
        DisplayDatabase::class.java, "database-name"
    ).build()
    // 多进程 enableMultiInstanceInvalidation
}

@Database(entities = [Word::class], version = 1)
class DisplayDatabase : RoomDatabase() {

//    Room.databaseBuilder()/Room.inMemoryDatabaseBuilder()

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {

    }

    override fun createInvalidationTracker(): InvalidationTracker {

    }

    override fun clearAllTables() {

    }
}