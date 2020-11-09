package com.example.selfdicwithjetpack.model.dic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.selfdicwithjetpack.model.dic.DicBean
import com.example.selfdicwithjetpack.model.dic.FieldBean
import com.example.selfdicwithjetpack.model.dic.WordBean
//
//import androidx.room.*
//
///**
// * Created by TpOut on 2020/10/20.<br>
// * Email address: 416756910@qq.com<br>
// *
// *     这里的关联查询，需要两个表的 id
// *     即 word.id 和 field.id，为了防止冲突
// *     进而在定义 entity 的时候就得分别确定为 word.word_id，field.field_id
// *     感觉就有点侵入式了啊
// */
//
///**
// * 词典和领域
// */
//@Entity(primaryKeys = ["dic_id", "field_id"],inheritSuperIndices = true)
//data class DicFieldCrossRef(
//    @ColumnInfo(name = "dic_id")
//    val dicId: Long,
//    @ColumnInfo(name = "field_id")
//    val fieldId: Long
//)
//
//data class DicAndFieldEntity(
//    @Embedded val dic: DicEntity,
//    @Relation(
//        parentColumn = "dic_id",
//        entityColumn = "field_id",
//        associateBy = Junction(DicFieldCrossRef::class)
//    )
//    val fields: List<FieldEntity>
//)
//
//data class FieldAndDicEntity(
//    @Embedded val field: FieldEntity,
//    @Relation(
//        parentColumn = "dic_id",
//        entityColumn = "field_id",
//        associateBy = Junction(DicFieldCrossRef::class)
//    )
//    val dics: List<DicEntity>
//)
//
///**
// * 词典和单词
// */
//@Entity(primaryKeys = ["dic_id", "word_id"])
//data class DicWordCrossRef(
//    @ColumnInfo(name = "dic_id")
//    val dicId: Long,
//    @ColumnInfo(name = "word_id")
//    val wordId: Long
//)
//
//data class DicAndWordEntity(
//    @Embedded val dic: DicEntity,
//    @Relation(
//        parentColumn = "dic_id",
//        entityColumn = "word_id",
//        associateBy = Junction(DicWordCrossRef::class)
//    )
//    val words: List<WordEntity>
//)
//
//data class WordAndDicEntity(
//    @Embedded val word: WordEntity,
//    @Relation(
//        parentColumn = "word_id",
//        entityColumn = "dic_id",
//        associateBy = Junction(DicWordCrossRef::class)
//    )
//    val dics: List<DicEntity>
//)
//
//// 如果使用常规的getter/setter，room 会认为是javabean
//// 构造函数的参数不满也没干系
////@Entity(primaryKeys = arrayOf("src","dst"),tableName = "users",ignoredColumns = arrayOf("pic"))
////@Fts3 使用全文检索的时候，primaryKey 一定要用rowId 作为列名称， FtsOptions
////@Fts4(languageId = "src")
@Entity(tableName = "dic",inheritSuperIndices = true)
class DicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dic_id")
    val id: Int = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    name: String
) : DicBean(name)

@Entity(tableName = "word",inheritSuperIndices = true)
class WordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    val id: Int,
    src: String?,
    dst: String?,
    sentence: String?
//    ,@Ignore val pic : String?
) : WordBean(src, dst, sentence)

@Entity(tableName = "field",inheritSuperIndices = true)
class FieldEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "field_id")
    val id: Int,
    name: String?
) : FieldBean(name)
//
//
//
