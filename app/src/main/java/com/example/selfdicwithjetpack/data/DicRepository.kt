package com.example.selfdicwithjetpack.data

import androidx.lifecycle.LiveData
import com.example.selfdicwithjetpack.model.dic.DicAndWordEntity
import com.example.selfdicwithjetpack.model.dic.DicDao
import com.example.selfdicwithjetpack.model.dic.WordBean

/**
 * Created by TpOut on 2020/10/23.<br>
 * Email address: 416756910@qq.com<br>
 */
class DicRepository(dicDao: DicDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<DicAndWordEntity>> = dicDao.getDicWordLiveData()

    suspend fun insert(word: WordBean) {
//        dicDao.insert(word)
    }
}