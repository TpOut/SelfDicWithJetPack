package com.example.selfdicwithjetpack.model.datax

import androidx.room.*
import com.example.selfdicwithjetpack.model.data.FieldBean
import com.example.selfdicwithjetpack.model.data.WordBean

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
 *
 *     这里的关联查询，需要两个表的 id
 *     即 word.id 和 field.id，为了防止冲突
 *     进而在定义 entity 的时候就得分别确定为 word.word_id，field.field_id
 *     感觉就有点侵入式了啊
 */

// n:n
@Entity(primaryKeys = ["word_id", "field_id"])
data class WordFieldCrossRef(
    @ColumnInfo(name = "word_id")
    val wordId: Long,
    @ColumnInfo(name = "field_id")
    val fieldId: Long
)

data class WordAndField(
    @Embedded val word: WordEntity,
    @Relation(
        parentColumn = "word_id",
        entityColumn = "field_id",
        associateBy = Junction(WordFieldCrossRef::class)
    )
    val fields: List<FieldBean>
)

data class FieldAndWord(
    @Embedded val field: FieldEntity,
    @Relation(
        parentColumn = "field_id",
        entityColumn = "word_id",
        associateBy = Junction(WordFieldCrossRef::class)
    )
    val words: List<WordBean>
)