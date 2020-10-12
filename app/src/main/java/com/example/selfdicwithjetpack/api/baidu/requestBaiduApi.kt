package com.example.selfdicwithjetpack.api.baidu
//
//import android.text.TextUtils
//import com.example.selfdicwithjetpack.crypto.getMD5
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.*
//
//
//suspend fun requestBaiduApi(query: String){
//        var retrofit: Retrofit? = Retrofit.Builder()
//            .baseUrl("https://fanyi-api.baidu.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        var baiduTransApi: BaiduTransApi = retrofit!!.create(BaiduTransApi::class.java)
//
//        var queryStr: String = query
//        if (TextUtils.isEmpty(queryStr)){
//            queryStr = "input is null"
//        }
//
//        var from = "en"
//        var to = "zh"
//        var appId = "20161013000030130"
//        var salt = Random().nextInt().toString()
//        var key = "KAF_utFypGQqkp9Ugfz_"
//
//        var sign: String = getMD5(appId + queryStr + salt + key)!!.toLowerCase(Locale.ROOT)
//
//        var call: Call<BaiduTransResultBean> = baiduTransApi.queryResult(
//            queryStr,
//            from,
//            to,
//            appId,
//            salt,
//            sign
//        )
//
//        val response = call.execute()
//
//        if (response.isSuccessful) {
//            val baiduTransResultBean: BaiduTransResultBean? = response.body()
//            if (null != baiduTransResultBean) {
//                val transResult = baiduTransResultBean.trans_result[0]
//                if (transResult.src == transResult.dst) {
//                    //没有翻译结果
//                    return
//                }
//            }
//        }
//    }