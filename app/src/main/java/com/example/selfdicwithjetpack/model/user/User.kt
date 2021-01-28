package com.example.selfdicwithjetpack.model.user

import androidx.room.*

/**
 * Created by TpOut on 2020/10/19.<br>
 * Email address: 416756910@qq.com<br>
 *     用户体系
 */

@Entity(tableName = "user", inheritSuperIndices = true)
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Int = 0,
    name: String?,
//    @TypeConverters(DateConverters::class) //注解是说支持field 的，但是写了没用
    birthday: String?,
    address: AddressBean?
//    @Embedded // 嵌入结构，实际上会作为User 表的直接column
) : UserBean(name, birthday, address) {
    override fun toString(): String {
        return "$id-${super.toString()}"
    }
}

open class UserBean(var name: String?, var birthday: String?, val address: AddressBean?) {
    override fun toString(): String {
        return "${name}-${birthday}-${address.toString()}"
    }
}

open class AddressBean(
    val city: String?,
    val street: String?
) {
    fun component1() = city

    fun component2() = street

    override fun toString(): String {
        return "$city-$street"
    }
}

class DateConverters {

    @TypeConverter
    fun toAddressBean(value: String?): AddressBean? {
        return value?.let {
            val results = it.split("-")
            AddressBean(results[0], results[1])
        }
    }

    @TypeConverter
    fun toString(address: AddressBean?): String? {
        return address?.let {
            it.city + "-" + it.street
        }
    }
}