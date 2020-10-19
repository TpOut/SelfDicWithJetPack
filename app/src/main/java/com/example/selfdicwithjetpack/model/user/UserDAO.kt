package com.example.selfdicwithjetpack.model.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

/**
 * Created by TpOut on 2020/10/16.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface UserDao {
    // 关联查询
    @Transaction
    @Query("SELECT * FROM user")
    fun getUserWord(): List<UserAndWord>

    @Transaction
    @Query("SELECT * FROM User")
    fun getUserField(): List<UserAndField>

//
//    @Query("SELECT * FROM Words WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

//    @Insert
//    fun insertAll(vararg users: User)
}