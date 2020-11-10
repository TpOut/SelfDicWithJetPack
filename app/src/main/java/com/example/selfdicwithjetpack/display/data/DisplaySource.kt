package com.example.selfdicwithjetpack.display.data

import androidx.paging.PagingSource
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.api.yourena.QueryWordList
import com.example.selfdicwithjetpack.api.yourena.QueryWordResultBean
import com.example.selfdicwithjetpack.display.DisplayBean
import com.example.selfdicwithjetpack.component.debug.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 *
 *     page 库，数据源 ： PagingSource, PagingMediator
 *
 *     RxPagingSource, ListenableFuturePagingSource
 *
 */
class DisplayPagingSource : PagingSource<Int, DisplayBean>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DisplayBean> {
        val page = params.key ?: PAGE_NUM_START
        LogUtils.d("DisplayPagingSource - load $page")
        return try {
            val service = QueryWordList.create()
            var result: Response<QueryWordResultBean>
            withContext(Dispatchers.IO) {
                result = service.queryWorldList(page).execute()
            }
            LogUtils.d("DisplayPagingSource - load $result")
            if (result.isSuccessful) {
                LogUtils.d("DisplayPagingSource - load success")
                val list = result.body()!!.result
                LoadResult.Page(
                    data = list,
                    prevKey = null,
                    nextKey = if (list.isEmpty() || list.size < PAGE_SIZE) null else page + 1
                )
            } else {
                LogUtils.d("DisplayPagingSource - load none")
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (exception: Exception) {
            LogUtils.d("DisplayPagingSource - load error")
            LoadResult.Error(exception)
        }
    }
}