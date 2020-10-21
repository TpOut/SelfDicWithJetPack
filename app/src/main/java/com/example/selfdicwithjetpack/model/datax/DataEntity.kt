package com.example.selfdicwithjetpack.model.datax

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.selfdicwithjetpack.model.data.AddressBean
import com.example.selfdicwithjetpack.model.data.DicBean
import com.example.selfdicwithjetpack.model.data.FieldBean
import com.example.selfdicwithjetpack.model.data.WordBean

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 *
 * 这里是因为没想好放哪个模块,姑且先这样放着
 * 纠结的点在于：
 *     用户可以很明确的分一个模块，所以UserEntity 放User 模块内部即可
 *
 *     而数据并不能自身做一个模块？WordEntity 就应该不属于数据自己，
 *     但是又不能直接放入User 模块，因为可能还会被其他模块依赖
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
    words: List<WordBean>
) : DicBean(words)

@Entity(tableName = "word")
class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "word_id")
    val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
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


@Entity(tableName = "address")
class AddressEntity(
    @PrimaryKey
    @ColumnInfo(name = "address_id")
    val id: Int,
    street: String,
    city: String
) : AddressBean(street, city)


