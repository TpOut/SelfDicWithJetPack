package com.example.selfdicwithjetpack.api.baidu

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
interface BaiduTransApi {
    @FormUrlEncoded
    @POST("/api/trans/vip/translate")
    fun queryResult(
        @Field("q") queryStr: String,
        @Field("from") srcLanguage: String,
        @Field("to") targetLanguage: String,
        @Field("appid") appid: String,
        @Field("salt") salt: String,
        @Field("sign") sign: String
    ): Call<BaiduTransResultBean>?
}