package com.example.selfdicwithjetpack.db

import androidx.room.*

/**
 * Created by TpOut on 2020/10/16.<br>
 * Email address: 416756910@qq.com<br>
 */

// 如果使用常规的getter/setter，room 会认为是javabean
// 构造函数的参数不满也没干系
//@Entity(primaryKeys = arrayOf("src","dst"),tableName = "users",ignoredColumns = arrayOf("pic"))
//@Fts3 使用全文检索的时候，primaryKey 一定要用rowId 作为列名称， FtsOptions
//@Fts4(languageId = "src")
@Entity()
data class Word(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    val src: String?,
    val dst: String?,
    @ColumnInfo(name = "sentence") val sentence: String?
//    ,@Ignore val pic : String?
)

@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String?,
    @Embedded(prefix = "text_")
    val address: Address?
)

// 嵌入结构，实际上会作为User 表的直接column
data class Address(
    val street: String?,
    val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int
)

// 对1:1 关联的表
data class UserAndLibrary(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val word: Word
)

// 1:n 关联的表
