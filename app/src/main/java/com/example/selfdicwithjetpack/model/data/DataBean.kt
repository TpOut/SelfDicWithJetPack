package com.example.selfdicwithjetpack.model.data

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */

// 地址
open class AddressBean(
    val street: String?,
    val city: String?
)

// 领域
open class FieldBean(var name: String?)

// 单词
open class WordBean(var src: String?, var dst: String?, var sentence: String?)