package com.example.selfdicwithjetpack.model.dic

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
// 单词本
open class DicBean(var name: String)

// 领域
open class FieldBean(var name: String?)

// 单词
open class WordBean(var src: String, var dst: String, var sentence: String?) {
    override fun toString(): String {
        return "WordBean{$src-$dst-$sentence}"
    }
}