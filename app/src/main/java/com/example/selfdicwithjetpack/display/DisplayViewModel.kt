package com.example.selfdicwithjetpack.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.selfdicwithjetpack.display.data.getSearchResultStream
import kotlinx.coroutines.flow.Flow

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

class DisplayViewModel : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<DisplayBean>>? = null

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