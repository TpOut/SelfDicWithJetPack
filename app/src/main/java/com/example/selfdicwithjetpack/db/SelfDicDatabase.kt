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

@Dao
interface UserDao {
    @Query("SELECT * FROM word")
    fun getAll(): List<Word>
//
//    @Query("SELECT * FROM Words WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

//    @Insert
//    fun insertAll(vararg users: User)

    @Delete
    fun delete(word: Word)
}

// 如果使用常规的getter/setter，room 会认为是javabean
// 构造函数的参数不满也没干系
//@Entity(primaryKeys = arrayOf("src","dst"),tableName = "users",ignoredColumns = arrayOf("pic"))
//@Fts3 使用全文检索的时候，primaryKey 一定要用rowId 作为列名称， FtsOptions
//@Fts4(languageId = "src")
@Entity()
data class Word(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    val src: String?,
    val dst: String?,
    @ColumnInfo(name = "sentence") val sentence: String?
//    ,@Ignore val pic : String?
)

@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String?,
    @Embedded(prefix = "text_")
    val address: Address?
)

data class Address(
    val street: String?,
    val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int
)