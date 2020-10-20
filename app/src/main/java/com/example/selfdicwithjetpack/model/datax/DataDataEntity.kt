package com.example.selfdicwithjetpack.model.datax

import androidx.room.*
import com.example.selfdicwithjetpack.model.data.FieldBean
import com.example.selfdicwithjetpack.model.data.WordBean

/**
 * Created by TpOut on 2020/10/20.<br>
 * Email address: 416756910@qq.com<br>
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
    @Embedded val word: WordBean,
    @Relation(
        parentColumn = "word_id",
        entityColumn = "field_id",
        associateBy = Junction(WordFieldCrossRef::class)
    )
    val fields: List<FieldBean>
)

data class FieldAndWord(
    @Embedded val field: FieldBean,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(WordFieldCrossRef::class)
    )
    val words: List<WordBean>
)