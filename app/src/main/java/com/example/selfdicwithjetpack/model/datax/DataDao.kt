package com.example.selfdicwithjetpack.model.datax

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.example.selfdicwithjetpack.model.data.WordBean

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface DataDao {

    @Query("SELECT * FROM word")
    fun getAll(): List<WordBean>

    @Delete
    fun delete(word: WordBean)

    //
    @Transaction
    @Query("SELECT * FROM word")
    fun getWordField(): List<WordAndField>
    @Transaction
    @Query("SELECT * FROM field")
    fun getFieldWord(): List<FieldAndWord>


}