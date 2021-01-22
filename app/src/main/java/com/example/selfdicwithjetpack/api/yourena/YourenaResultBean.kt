package com.example.selfdicwithjetpack.api.yourena

import com.example.selfdicwithjetpack.model.dic.WordEntity

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 *
 * resultStatus : success / fail
 */
data class QueryWordResultBean(val resultStatus: String, val result: List<WordEntity>, val page: Int)
data class AddWordResultBean(val resultStatus: String)