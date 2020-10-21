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

    @Query("SELECT * FROM user WHERE user_id IN (:userId)")
    fun getUser(userId : Int) : UserEntity

//    @Insert
//    fun insertAll(vararg users: User)
}