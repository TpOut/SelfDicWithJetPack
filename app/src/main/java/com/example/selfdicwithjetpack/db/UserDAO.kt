package com.example.selfdicwithjetpack.db

import android.content.Context
import androidx.room.*

/**
 * Created by TpOut on 2020/10/16.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM word")
    fun getAll(): List<Word>

    // 关联查询
    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersAndLibraries(): List<UserAndLibrary>
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

@Dao
interface WordDao{

}