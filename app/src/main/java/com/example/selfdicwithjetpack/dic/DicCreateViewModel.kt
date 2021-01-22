package com.example.selfdicwithjetpack.dic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.selfdicwithjetpack.component.data.Sp
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.model.dic.DicEntity
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2020/11/9.<br>
 * Email address: 416756910@qq.com<br>
 */
const val DIC_CREATE_VIEW_MODEL_TAG = "DicCreateViewModel"

class DicCreateViewModel : ViewModel() {

    fun insertDic(dicName: String) {
        viewModelScope.launch {
            val userId = Sp.queryUserId()
            AppDb.appDb.withTransaction {
                AppDb.appDb.dicDao().insertDic(DicEntity(userId = userId, name = dicName))
            }
        }
    }

}