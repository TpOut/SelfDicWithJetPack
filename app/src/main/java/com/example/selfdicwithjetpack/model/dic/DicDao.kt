package com.example.selfdicwithjetpack.model.dic
//
//import androidx.lifecycle.LiveData
//import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query

//import androidx.room.Delete
//import androidx.room.Query
//import androidx.room.Transaction
//
///**
// * Created by TpOut on 2020/10/21.<br>
// * Email address: 416756910@qq.com<br>
// */
@Dao
interface DicDao {
    @Query("SELECT * FROM dic")
    fun getAllDics(): List<DicEntity>

//    @Query("SELECT * FROM word")
//    fun getAllWords(): List<WordEntity>
//
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