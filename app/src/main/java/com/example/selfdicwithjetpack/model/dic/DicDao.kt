package com.example.selfdicwithjetpack.model.dic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface DicDao {
    @Query("SELECT * FROM word")
    fun getAllWords(): List<WordBean>

    @Delete
    fun delete(word: WordBean)

    //
    @Transaction
    @Query("SELECT * FROM dic WHERE dic_id == :dicId")
    fun getDicField(dicId: String): List<DicAndFieldEntity>

    @Transaction
    @Query("SELECT * FROM field")
    fun getFieldDic(): List<FieldAndDicEntity>
}