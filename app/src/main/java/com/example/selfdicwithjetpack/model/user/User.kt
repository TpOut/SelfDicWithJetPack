package com.example.selfdicwithjetpack.model.user

import androidx.room.*

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 *     用户体系
 */

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: Int,
    name: String?,
    val birthday: DateBean?,
    @Embedded(prefix = "addr_") // 嵌入结构，实际上会作为User 表的直接column
    val address: AddressBean?
) : UserBean(name)

@Entity(tableName = "address")
class AddressEntity(
    @PrimaryKey
    val id: Int,
    street: String,
    city: String
) : AddressBean(street, city)

open class UserBean(var name: String?)

open class AddressBean(
    val street: String?,
    val city: String?
)

open class DateBean(
    val year: String?,
    val month: String?,
    val day: String?
)

class Converters {
    @TypeConverter
    fun dateFromString(value: String?): DateBean? {
        return value?.let {
            DateBean(
                value.substring(0, 4),
                value.substring(4, 6),
                value.substring(6, 8)
            )
        }
    }

    @TypeConverter
    fun dateToString(date: DateBean?): String? {
        return date?.let { it.year + it.month + it.day }
    }
}