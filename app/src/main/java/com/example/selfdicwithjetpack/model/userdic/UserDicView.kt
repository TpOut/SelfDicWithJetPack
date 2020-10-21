package com.example.selfdicwithjetpack.model.userdic

import androidx.room.Database
import androidx.room.DatabaseView
import androidx.room.RoomDatabase
import com.example.selfdicwithjetpack.model.user.UserDao

/**
 * Created by TpOut on 2020/10/21.<br>
 * Email address: 416756910@qq.com<br>
 */
@DatabaseView("SELECT user.id, user.name, user.departmentId," +
        "department.name AS departmentName FROM user " +
        "INNER JOIN department ON user.departmentId = department.id")
data class UserDetail(
    val id: Long,
    val name: String?,
    val departmentId: Long,
    val departmentName: String?
)

//@Database(entities = arrayOf(User::class),
//    views = arrayOf(UserDetail::class), version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}
