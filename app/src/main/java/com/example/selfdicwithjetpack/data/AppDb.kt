package com.example.selfdicwithjetpack.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blankj.utilcode.util.Utils
import com.example.selfdicwithjetpack.model.dic.DicDao
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.example.selfdicwithjetpack.model.dic.FieldEntity
import com.example.selfdicwithjetpack.model.dic.WordEntity
import com.example.selfdicwithjetpack.model.user.DateConverters
import com.example.selfdicwithjetpack.model.user.UserDao
import com.example.selfdicwithjetpack.model.user.UserEntity
import java.util.concurrent.Executors

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 *
 * Room executes all queries on a separate thread.
 * Observed LiveData will notify the observer when the data has changed.
 *
 * val allWords: LiveData<List<DicAndWordEntity>> = dicDao.getDicWordLiveData()
 *
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
        val addDb: AppDb by lazy {
            Room.databaseBuilder(
                Utils.getApp(),
                AppDb::class.java, "yourena"
            )
                .setQueryExecutor(Executors.newSingleThreadExecutor())
                .setTransactionExecutor(Executors.newCachedThreadPool())
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
