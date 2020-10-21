package com.example.selfdicwithjetpack.model.userdic

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface UserDicDao {
    // 关联查询
    @Transaction
    @Query("SELECT * FROM user WHERE user_id == :userId")
    fun getUserDic(userId: Int): List<UserAndDicEntity>

    // 嵌套关联
    @Transaction
    @Query("SELECT * FROM User")
    fun getUserDicField(): List<UserAndDicAndField>

}