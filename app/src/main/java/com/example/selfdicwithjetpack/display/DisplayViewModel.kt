package com.example.selfdicwithjetpack.display

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.display.data.DisplayPagingSource
import com.example.selfdicwithjetpack.display.data.PAGE_SIZE
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.example.selfdicwithjetpack.model.dic.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

const val DISPLAY_VIEW_MODEL_TAG = "DisplayViewModel"

class DisplayViewModel : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<DisplayBean>>? = null

    //这里有个纠结点，本来想用lazy 做一个协程async 返回结果。实际上是不可行的
    //todo 数据库设置有查询线程，不知道这里可不可以，注解是说所有suspend
    val dicList: LiveData<List<DicEntity>> by lazy {
        AppDb.appDb.dicDao().getAllDics()
    }
    //val allWords: LiveData<List<WordEntity>> = repo.allWords

    fun insert(word: WordEntity) = viewModelScope.launch(Dispatchers.IO) {
        // AppDb.getDisplayDb().dicDao().insert(word)
    }

    // 支持多种
// Flow, LiveData, and the Flowable and Observable types from RxJava.
    fun fetchData(dicId: String?): Flow<PagingData<DisplayBean>> {
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