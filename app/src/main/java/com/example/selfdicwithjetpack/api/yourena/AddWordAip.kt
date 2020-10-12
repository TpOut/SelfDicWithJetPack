package com.example.selfdicwithjetpack.api.yourena

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
interface AddWordAip {
    @FormUrlEncoded
    @POST("/AddWord")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun uploadResult(
        @Field("src") queryStr: String,
        @Field("result") srcLanguage: String,
        @Field("sentence") targetLanguage: String
    ): Call<String>?
}