package com.example.selfdicwithjetpack.model.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.selfdicwithjetpack.model.data.AddressBean

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 *     用户体系
 */
open class UserBean(var name: String?)

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey val id: Int,
    name: String?,
    @Embedded(prefix = "text_") // 嵌入结构，实际上会作为User 表的直接column
    val address: AddressBean?
) : UserBean(name)