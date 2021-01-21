package com.example.selfdicwithjetpack.api.baidu

import android.text.TextUtils
import com.example.selfdicwithjetpack.component.crypto.getMD5
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

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

    companion object {
        fun create(): BaiduTransApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://fanyi-api.baidu.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BaiduTransApi::class.java)
        }

        fun createQuery(query: String): Call<BaiduTransResultBean>? {
            var queryStr: String = query
            if (TextUtils.isEmpty(queryStr)) {
                queryStr = "input is null"
            }

            val from = "en"
            val to = "zh"
            val appId = "20161013000030130"
            val salt = Random().nextInt().toString()
            val key = "KAF_utFypGQqkp9Ugfz_"
            val sign: String = getMD5(appId + queryStr + salt + key)!!.toLowerCase(Locale.ROOT)

            return create().queryResult(
                queryStr,
                from,
                to,
                appId,
                salt,
                sign
            )
        }
    }

}