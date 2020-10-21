package com.example.selfdicwithjetpack.model.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.selfdicwithjetpack.model.data.FieldBean
import com.example.selfdicwithjetpack.model.data.WordBean
import com.example.selfdicwithjetpack.model.datax.WordEntity
import java.lang.reflect.Field

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 */


// 对1:1 关联的表
data class UserAndWord(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val word: WordEntity
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

//
data class UserAndWordAndField {
    @Embedded val user: UserBean
    @Relation(
        entity = Playlist::class,
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val playlists: List<PlaylistWithSongs>
}
