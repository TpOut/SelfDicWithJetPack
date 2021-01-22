package com.example.selfdicwithjetpack.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.room.withTransaction
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.api.NetworkCenter
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.display.data.DisplayMediator
import com.example.selfdicwithjetpack.display.data.DisplayPagingSource
import com.example.selfdicwithjetpack.display.data.PAGE_SIZE
import com.example.selfdicwithjetpack.model.dic.DicEntity
import com.example.selfdicwithjetpack.model.dic.WordEntity
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

const val DISPLAY_VIEW_MODEL_TAG = "DisplayViewModel"

class DisplayViewModel : ViewModel() {

    private var currentQueryValue: String? = null
    private var mCurrentSearchResult: Flow<PagingData<DisplayUIModel>>? = null

    //这里有个纠结点，本来想用lazy 做一个协程async 返回结果。实际上是不可行的
    //todo 数据库设置有查询线程，不知道这里可不可以，注解是说所有suspend
    private var dicListJob: Deferred<Flow<List<DicEntity>>>? = null
    suspend fun getDicListDeferred(): Flow<List<DicEntity>>? {
        if (null == dicListJob) {
            dicListJob = queryDicList()
        }
        return dicListJob?.await()
    }

    private fun queryDicList(): Deferred<Flow<List<DicEntity>>> {
        return viewModelScope.async(start = CoroutineStart.LAZY) {
            AppDb.appDb.withTransaction {
                AppDb.appDb.dicDao().getAllDics()
            }
        }
    }

    //val allWords: LiveData<List<WordEntity>> = repo.allWords

    fun fetchRoomData(): Flow<PagingData<DisplayUIModel.DisplayItemModel>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
//                AppDb.appDb.withTransaction //todo
                AppDb.appDb.dicDao().getWordsPagingSource()
            }
        ).flow
            .map { pageData ->
                pageData.map { wordEntity ->
                    LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchRoomData map ${wordEntity.src}")
                    DisplayUIModel.DisplayItemModel(wordEntity.src, wordEntity.dst, wordEntity.sentence ?: "")
                }
            }
            .cachedIn(viewModelScope)
    }

    //从网络获取到数据库
    @OptIn(ExperimentalPagingApi::class)
    fun fetchMediatorData(): Flow<PagingData<DisplayUIModel>> {
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
                pageData
                    .map { wordEntity ->
                        LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchMediatorData map ${wordEntity.src}")
                        DisplayUIModel.DisplayItemModel(wordEntity.src, wordEntity.dst, wordEntity.sentence ?: "")
                    }
                    .insertSeparators<DisplayUIModel.DisplayItemModel, DisplayUIModel> { before, after ->
                        LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "insertSeparators : $before - $after ")
                        when {
                            before == null -> DisplayUIModel.DisplayHeaderModel()
                            after == null -> null
//                            shouldSeparate(before, after) -> UiModel.SeparatorModel(
//                                "BETWEEN ITEMS $before AND $after"
//                            )
                            // Return null to avoid adding a separator between two items.
                            else -> null
                        }
                    }
            }
            .cachedIn(viewModelScope)
    }

    // 只是从网络获取
    // 支持多种
    // Flow, LiveData, and the Flowable and Observable types from RxJava.
    fun fetchData(dicId: String?): Flow<PagingData<DisplayUIModel.DisplayItemModel>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = { DisplayPagingSource() }
        ).flow
            .map { pageData ->
                pageData
                    .map { wordEntity ->
                        LogUtils.d(DISPLAY_VIEW_MODEL_TAG, "fetchMediatorData map ${wordEntity.src}")
                        DisplayUIModel.DisplayItemModel(wordEntity.src, wordEntity.dst, wordEntity.sentence ?: "")
                    }
            }
            .cachedIn(viewModelScope)
    }

    fun fetchNextData() {

    }

    suspend fun queryWord(query: String, sentence: String): Boolean {
        val result = NetworkCenter.queryBaiduTransAndUpload2Yourena(query, sentence)
        AppDb.appDb.withTransaction {
            // TODO: 1/22/21 这里的时间现在本地写，后续可以考虑后台返回
            AppDb.appDb.dicDao().insertWord(WordEntity(src = query, dst = result, sentence = sentence, createTime = System.currentTimeMillis()))
        }
        return result.isNotEmpty()
    }

}