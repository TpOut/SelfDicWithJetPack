package com.example.selfdicwithjetpack.display

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.display.data.DisplayMediator
import com.example.selfdicwithjetpack.display.data.DisplayPagingSource
import com.example.selfdicwithjetpack.display.data.PAGE_NUM_START
import com.example.selfdicwithjetpack.display.data.PAGE_SIZE
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.example.selfdicwithjetpack.model.dic.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
    val dicList: Flow<List<DicEntity>> by lazy {
        AppDb.appDb.dicDao().getAllDics()
    }
    //val allWords: LiveData<List<WordEntity>> = repo.allWords

    fun insert(word: WordEntity) = viewModelScope.launch(Dispatchers.IO) {
        // AppDb.getDisplayDb().dicDao().insert(word)
    }

    fun fetchRoomData(): Flow<PagingData<DisplayBean>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchRoomData time +1 ")
                AppDb.appDb.dicDao().getWordsPagingSource()
            }
        ).flow
            .map { pageData ->
                pageData.map { wordEntity ->
                    LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchRoomData map ${wordEntity.src}")
                    DisplayBean(wordEntity.src, wordEntity.dst, wordEntity.sentence ?: "")
                }
            }
            .cachedIn(viewModelScope)
    }

    //从网络获取到数据库
    fun fetchMediatorData(): Flow<PagingData<DisplayBean>> {
        val dao = AppDb.appDb.dicDao()
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = DisplayMediator(),
            pagingSourceFactory = {
                LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "getWordsPagingSource")
                val wordsPagingSource = dao.getWordsPagingSource()
                LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "getWordsPagingSource $wordsPagingSource")
                wordsPagingSource.registerInvalidatedCallback {
                    LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "invalidate + 1")
                }
                wordsPagingSource
            }
        ).flow
            .map { pageData ->
                pageData.map { wordEntity ->
                    LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchMediatorData map ${wordEntity.src}")
                    DisplayBean(wordEntity.src, wordEntity.dst, wordEntity.sentence ?: "")
                }
            }
            .cachedIn(viewModelScope)
    }

    // 只是从网络获取
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