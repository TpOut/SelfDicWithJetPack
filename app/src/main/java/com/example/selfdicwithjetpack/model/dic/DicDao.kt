package com.example.selfdicwithjetpack.model.dic

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
@Dao
interface DicDao {
    @Query("SELECT * FROM dic")
    fun getAllDics(): Flow<List<DicEntity>>

    @Insert
    suspend fun insertDic(dic: DicEntity)

    @Insert
    suspend fun insertWord(word: WordEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<WordEntity>)

    @Query("SELECT * FROM word ORDER BY create_time DESC")
    fun getWordsPagingSource(): PagingSource<Int, WordEntity>

    @Query("DELETE FROM word")
    suspend fun clearAllWords()
//    @Delete
//    fun delete(word: WordEntity)
//
//    //
//    @Transaction
//    @Query("SELECT * FROM dic WHERE dic_id == :dicId")
//    fun getDicField(dicId: String): List<DicAndFieldEntity>
//
//    @Transaction
//    @Query("SELECT * FROM field")
//    fun getFieldDic(): List<FieldAndDicEntity>
//
//    //
//    @Transaction
//    @Query("SELECT * FROM dic WHERE dic_id == :dicId")
//    fun getDicWord(dicId: String): List<DicAndWordEntity>
//
//    //不知道可不可以
//    fun getDicWordPagable(query: String): PagingSource<Int, List<DicAndWordEntity>>
//    fun getDicWordLiveData(): LiveData<List<DicAndWordEntity>>
}