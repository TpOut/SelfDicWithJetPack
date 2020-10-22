package com.example.selfdicwithjetpack.model.userdic

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface UserDicDao {


    // 关联查询
    @Transaction
    @Query("SELECT * FROM user WHERE user_id == :userId")
    suspend fun getUserDic(userId: Int): Flow<UserAndDicEntity>

    suspend fun getUserDicDistinctUntilChanged(userId: Int) = getUserDic(userId).distinctUntilChanged()

    // 嵌套关联
    @Transaction
    @Query("SELECT * FROM User")
    fun getUserDicField(): List<UserAndDicAndField>

}