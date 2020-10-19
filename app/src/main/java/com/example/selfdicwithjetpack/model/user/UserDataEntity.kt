package com.example.selfdicwithjetpack.model.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.selfdicwithjetpack.model.data.FieldBean
import com.example.selfdicwithjetpack.model.data.WordBean
import java.lang.reflect.Field

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */


// 对1:1 关联的表
data class UserAndWord(
    @Embedded val user: UserBean,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val word: WordBean
)

// 1:n 关联的表
data class UserAndField(
    @Embedded val user: UserBean,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val fields: List<FieldBean>
)


// n:1 关联的表
