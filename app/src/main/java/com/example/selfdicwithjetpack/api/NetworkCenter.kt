package com.example.selfdicwithjetpack.api

import com.example.selfdicwithjetpack.api.baidu.BaiduTransApi
import com.example.selfdicwithjetpack.api.baidu.BaiduTransResultBean
import com.example.selfdicwithjetpack.api.yourena.AddWordApi
import com.example.selfdicwithjetpack.api.yourena.AddWordResultBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by TpOut on 2021/1/21.<br>
 * Email address: 416756910@qq.com<br>
 */
const val RESPONSE_SUCCESS = "success"
const val RESPONSE_FAIL = "fail"

class NetworkCenter {
    companion object {

        suspend fun queryBaiduTrans(query: String): String {
            var result: String = ""
            var response: Response<BaiduTransResultBean>? = null
            withContext(Dispatchers.IO) {
                response = BaiduTransApi.createQuery(query)?.execute()
                if (response?.isSuccessful == true) {
                    val baiduTransResultBean: BaiduTransResultBean? = response?.body()
                    if (null != baiduTransResultBean) {
                        val transResult = baiduTransResultBean.trans_result[0]
                        if (transResult.src != transResult.dst) {
                            result = transResult.dst
                        }
                    }
                }
            }
            return result
        }

        suspend fun upload2Yourena(src: String, dst: String, sentence: String): Boolean {
            var result: Boolean = false
            var response: Response<AddWordResultBean>? = null
            withContext(Dispatchers.IO) {
                response = AddWordApi.create().uploadResult(src, dst, sentence).execute()
                if (response?.isSuccessful == true && response?.body()?.resultStatus.equals(RESPONSE_SUCCESS)) {
                    result = true
                }
            }
            return result
        }

        suspend fun queryBaiduTransAndUpload2Yourena(query: String, sentence: String): String {
            val queryResult = queryBaiduTrans(query)
            if (queryResult.isNotEmpty()) {
                val success = upload2Yourena(query, queryResult, sentence)
                if(success){
                    return queryResult
                }
            }
            return ""
        }
    }

}