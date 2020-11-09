package com.example.selfdicwithjetpack.display

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.display.data.DisplayPagingSource
import com.example.selfdicwithjetpack.display.data.PAGE_SIZE
import com.example.selfdicwithjetpack.model.dic.DicEntity
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


//    val allWords: LiveData<List<WordEntity>> = repo.allWords

    fun insert(word: WordEntity) = viewModelScope.launch(Dispatchers.IO) {
        // AppDb.getDisplayDb().dicDao().insert(word)
    }

    @WorkerThread
    suspend fun fetchDicList(): List<String> {
        return AppDb.appDb.dicDao().getAllDics().map {
            it.name
        }
    }

    // 支持多种
    // Flow, LiveData, and the Flowable and Observable types from RxJava.
    fun fetchData(dicId: String): Flow<PagingData<DisplayBean>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = { DisplayPagingSource() }
        ).flow.cachedIn(viewModelScope)
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