package com.example.selfdicwithjetpack.display.data

import androidx.paging.PagingSource
import com.example.selfdicwithjetpack.api.yourena.QueryWordList
import com.example.selfdicwithjetpack.api.yourena.QueryWordResultBean
import com.example.selfdicwithjetpack.display.DisplayBean
import com.example.selfdicwithjetpack.component.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
const val PAGE_NUM_START = 1

class DisplayPagingSource : PagingSource<Int, DisplayBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DisplayBean> {
        val page = params.key ?: PAGE_NUM_START
        LogUtil.d("DisplayPagingSource - load")
        return try {
            val service = QueryWordList.create()
            LogUtil.d("DisplayPagingSource - result")
            var result: Response<QueryWordResultBean>
            withContext(Dispatchers.IO) {
                result = service.queryWorldList(page).execute()
            }
            LogUtil.d("DisplayPagingSource - result")
            if (result.isSuccessful) {
                val list = result.body()!!.result
                LoadResult.Page(
                    data = list,
                    prevKey = if (page == PAGE_NUM_START) null else page - 1,
                    nextKey = if (list.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Page(
                    data = listOf(),
                    prevKey = if (page == PAGE_NUM_START) null else page - 1,
                    nextKey = null
                )
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}