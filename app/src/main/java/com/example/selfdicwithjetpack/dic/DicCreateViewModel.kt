package com.example.selfdicwithjetpack.dic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.component.data.Sp
import com.example.selfdicwithjetpack.data.AppDb
import com.example.selfdicwithjetpack.model.dic.DicEntity
import kotlinx.coroutines.async

/**
 * Created by TpOut on 2020/11/9.<br>
 * Email address: 416756910@qq.com<br>
 */
const val DIC_CREATE_VIEW_MODEL_TAG = "DicCreateViewModel"

class DicCreateViewModel : ViewModel() {

    fun insertDic(dicName: String) {
        viewModelScope.async {
            val userId = Sp.queryUserId()
            LogUtils.d(DIC_CREATE_VIEW_MODEL_TAG, "start insert")
            AppDb.appDb.dicDao().insertDic(DicEntity(userId = userId, name = dicName))
            LogUtils.d(DIC_CREATE_VIEW_MODEL_TAG, "end insert")
        }
    }

}