package com.example.selfdicwithjetpack.api.baidu

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */
data class Trans_result(var src: String, var dst: String)
data class BaiduTransResultBean(var from: String, var to: String, var trans_result: List<Trans_result>)