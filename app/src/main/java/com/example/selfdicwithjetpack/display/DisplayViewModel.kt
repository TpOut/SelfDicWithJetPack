package com.example.selfdicwithjetpack.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.Utils
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.data.DicRepository
import com.example.selfdicwithjetpack.display.data.getSearchResultStream
import com.example.selfdicwithjetpack.model.dic.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

class DisplayViewModel : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<DisplayBean>>? = null


    private var repo: DicRepository = DicRepository(AppDb.getDisplayDb(Utils.getApp()).dicDao())

//    val allWords: LiveData<List<WordEntity>> = repo.allWords

    fun insert(word: WordEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(word)
    }

    fun fetchData(): Flow<PagingData<DisplayBean>> {
        return getSearchResultStream().cachedIn(viewModelScope)
    }

    fun fetchNextData() {

    }

    fun queryWord() {
//        currentQueryValue = queryString
//        val newResult: Flow<PagingData<DisplayBean>> = repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
//        currentSearchResult = newResult
//        return newResult
    }


}