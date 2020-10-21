package com.example.selfdicwithjetpack.model.dic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
// 如果使用常规的getter/setter，room 会认为是javabean
// 构造函数的参数不满也没干系
//@Entity(primaryKeys = arrayOf("src","dst"),tableName = "users",ignoredColumns = arrayOf("pic"))
//@Fts3 使用全文检索的时候，primaryKey 一定要用rowId 作为列名称， FtsOptions
//@Fts4(languageId = "src")
@Entity(tableName = "dic")
class DicEntity(
    @PrimaryKey
    @ColumnInfo(name = "dic_id")
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    name: String
) : DicBean(name)

@Entity(tableName = "word")
class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "word_id")
    val id: Int,
    @ColumnInfo(name = "dic_id")
    val dicId: Int,
    src: String?,
    dst: String?,
    sentence: String?
//    ,@Ignore val pic : String?
) : WordBean(src, dst, sentence)

@Entity(tableName = "field")
class FieldEntity(
    @PrimaryKey
    @ColumnInfo(name = "field_id")
    val id: Int,
    name: String?
) : FieldBean(name)

// 单词本
open class DicBean(var name: String)

// 领域
open class FieldBean(var name: String?)

// 单词
open class WordBean(var src: String?, var dst: String?, var sentence: String?)