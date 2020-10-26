package com.example.selfdicwithjetpack.model.user

import androidx.room.*


/**
 * Created by TpOut on 2020/10/16.<br>
 * Email address: 416756910@qq.com<br>
 *     这个算是上层业务了，不算模块，先写在这里
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(vararg user: UserEntity): List<Long>


    //    @Query("SELECT * FROM user WHERE user_id IN (:userId)")
//    fun getSimpleUser(userId : Int) : UserBean
    @Query("SELECT * FROM user WHERE user_id == :userId")
    suspend fun getUserInfo(userId: String): UserEntity

    @Query("SELECT * FROM user")
    suspend fun getCurrentUserInfo(): List<UserEntity>?

}