package com.example.selfdicwithjetpack.api.yourena

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

const val BASE_URL_YOURENA = "http://www.tpout.com"

// 向后台添加单词
interface AddWordApi {
    @FormUrlEncoded
    @POST("/AddWord")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun uploadResult(
        @Field("src") queryStr: String,
        @Field("result") srcLanguage: String,
        @Field("sentence") targetLanguage: String
    ): Call<String>

    companion object {
        fun create(): AddWordApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_YOURENA)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AddWordApi::class.java)
        }
    }
}

// 查询所有单词
interface QueryWordList {

    @GET("/WordList")
    fun queryWorldList(@Query("pageNum") pageNum : String): Call<QueryWordResultBean>

    companion object {
        fun create(): QueryWordList {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_YOURENA)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QueryWordList::class.java)
        }
    }
}