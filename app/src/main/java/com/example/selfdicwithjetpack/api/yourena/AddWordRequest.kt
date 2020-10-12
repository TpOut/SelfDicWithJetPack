package com.example.selfdicwithjetpack.api.yourena
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
///**
// * Created by TpOut on 2020/10/12.<br>
// * Email address: 416756910@qq.com<br>
// */
//fun upload(src: String?, result: String?, sentence: String?): Unit {
//    val retrofit = Retrofit.Builder()
//        .baseUrl("http://www.tpout.com")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val api = retrofit.create(AddWordAip)
//    val call: Call<String> = api.uploadResult(
//        src, result, sentence
//    )
//    call.enqueue(object : Callback<String?> {
//        override fun onResponse(call: Call<String?>, response: Response<String?>) {
//            LogUtil.d("sdsdfsd", "fsfsddfsdf")
//        }
//
//        override fun onFailure(call: Call<String?>, t: Throwable) {
//            LogUtil.d("sdsdfsd", "fsfsddfsdf")
//        }
//    })
//}