package com.example.selfdicwithjetpack.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

class DisplayViewModel : ViewModel(){

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<DisplayBean>>? = null

    fun fetchData(pageNum : Int) : Flow<PagingData<DisplayBean>{
        currentQueryValue = queryString
        val newResult: Flow<PagingData<DisplayBean>> = repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}