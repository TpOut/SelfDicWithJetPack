//package com.example.selfdicwithjetpack.model.userdic
//
//import androidx.room.Embedded
//import androidx.room.Relation
//import com.example.selfdicwithjetpack.model.dic.DicAndFieldEntity
//import com.example.selfdicwithjetpack.model.dic.DicEntity
//import com.example.selfdicwithjetpack.model.user.UserEntity
//
///**
// * Created by TpOut on 2020/10/21.<br>
// * Email address: 416756910@qq.com<br>
// */
//// 关联的表，使用List 或者Bean 表示 1:n 或者 1:1
//data class UserAndDicEntity(
//    @Embedded
//    val user: UserEntity,
//    @Relation(
//        parentColumn = "user_id",
//        entityColumn = "word_id"
//    )
//    val dics: List<DicEntity>
//)
//
////直接查询下级嵌套
//data class UserAndDicAndField(
//    @Embedded val user: UserEntity,
//    @Relation(
//        entity = DicEntity::class,
//        parentColumn = "user_id",
//        entityColumn = "word_id"
//    )
//    val dicFields: List<DicAndFieldEntity>
//)
