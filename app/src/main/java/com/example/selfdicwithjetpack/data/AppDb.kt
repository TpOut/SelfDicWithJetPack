package com.example.selfdicwithjetpack.data

import android.content.Context
import androidx.room.*
import com.example.selfdicwithjetpack.model.dic.DicDao
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.example.selfdicwithjetpack.model.dic.FieldEntity
import com.example.selfdicwithjetpack.model.dic.WordEntity
import com.example.selfdicwithjetpack.model.user.DateConverters
import com.example.selfdicwithjetpack.model.user.UserDao
import com.example.selfdicwithjetpack.model.user.UserEntity

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
@Database(
    entities = [
        DicEntity::class,
        WordEntity::class,
        FieldEntity::class,
        UserEntity::class
    ], version = 1
)
@TypeConverters(DateConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dicDao(): DicDao
//    abstract fun userDicDao(): UserDicDao

    companion object {
        // 多进程 enableMultiInstanceInvalidation
        fun getDisplayDb(appContext: Context): AppDb {
            return Room.databaseBuilder(
                appContext,
                AppDb::class.java, "yourena"
            )
                .addCallback(object : RoomDatabase.Callback() {

                })
                .build()
        }
    }

//    Room.databaseBuilder()/Room.inMemoryDatabaseBuilder()

//        override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
//
//        }
//
//        override fun createInvalidationTracker(): InvalidationTracker {
//
//        }
//
//        override fun clearAllTables() {
//
//        }
}
