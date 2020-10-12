package com.example.selfdicwithjetpack.display.data

import androidx.paging.PagingSource
import com.example.selfdicwithjetpack.api.yourena.QueryWordList
import com.example.selfdicwithjetpack.display.DisplayBean

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
const val PAGE_NUM_START = 1

class DisplayPagingSource(
    private val pageNum: String
) : PagingSource<Int, DisplayBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DisplayBean> {
        val page = params.key ?: PAGE_NUM_START
        return try {
            val response = QueryWordList.create()
            val photos = response.queryWorldList(pageNum)
            LoadResult.Page(
                data = photos,
                prevKey = if (page == PAGE_NUM_START) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}