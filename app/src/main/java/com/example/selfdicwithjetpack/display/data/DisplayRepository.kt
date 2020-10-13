package com.example.selfdicwithjetpack.display.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.selfdicwithjetpack.display.DisplayBean
import kotlinx.coroutines.flow.Flow

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
fun getSearchResultStream(): Flow<PagingData<DisplayBean>> {
    return Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 5),
        pagingSourceFactory = { DisplayPagingSource() }
    ).flow
}

