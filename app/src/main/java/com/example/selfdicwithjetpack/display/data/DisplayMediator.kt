package com.example.selfdicwithjetpack.display.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.api.yourena.QueryWordList
import com.example.selfdicwithjetpack.api.yourena.QueryWordResultBean
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.model.dic.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Created by TpOut on 2020/11/10.<br>
 * Email address: 416756910@qq.com<br>
 *
 */
const val DISPLAY_MEDIATOR_TAG = "DisplayMediator"

@OptIn(ExperimentalPagingApi::class)
class DisplayMediator() : RemoteMediator<Int, WordEntity>() {
    private val dicDao = AppDb.appDb.dicDao()
    private var mPage = PAGE_NUM_START

    override suspend fun load(loadType: LoadType, state: PagingState<Int, WordEntity>): MediatorResult {
        return try {
            LogUtils.d(
                DISPLAY_MEDIATOR_TAG, "loadType :" + " $loadType -- ${state.anchorPosition} -- ${state.pages}"
            )
            if (!state.pages.isNullOrEmpty()) {
                LogUtils.d(DISPLAY_MEDIATOR_TAG, "last page item : ${state.pages.lastOrNull()?.data?.lastOrNull()?.src}")
            }
            val page = when (loadType) {
                LoadType.REFRESH -> mPage
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItemOrNull = state.lastItemOrNull()
                    lastItemOrNull ?: return MediatorResult.Success(endOfPaginationReached = true)
                    LogUtils.d(DISPLAY_MEDIATOR_TAG, "nextKey : ${state.pages.last().nextKey}")
                    mPage + 1
                }
            }
            var result: Response<QueryWordResultBean>
            withContext(Dispatchers.IO) {
                LogUtils.d(DISPLAY_MEDIATOR_TAG, "queryWorldList $page")
                result = QueryWordList.create().queryWorldList(page).execute()
            }
            LogUtils.d(DISPLAY_MEDIATOR_TAG, "result isSuccessful : ${result.isSuccessful}")
            if (result.isSuccessful) {
                val list = result.body()!!.result
                list.forEachIndexed { index, displayBean ->
                    LogUtils.d(DISPLAY_MEDIATOR_TAG, "result item $index: ${displayBean.src}")
                }
                if (list.isNullOrEmpty()) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                val dbList = list.map { WordEntity(src = it.src, dst = it.dst, sentence = it.sentence) }
                AppDb.appDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        LogUtils.d(DISPLAY_MEDIATOR_TAG, "do clearAll")
                        dicDao.clearAllWords()
                    }
                    LogUtils.d(DISPLAY_MEDIATOR_TAG, "do insert")
                    dicDao.insertWords(dbList)
                }
                mPage++
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
