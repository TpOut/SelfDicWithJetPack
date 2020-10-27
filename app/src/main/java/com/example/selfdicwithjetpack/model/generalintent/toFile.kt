package com.example.selfdicwithjetpack.model.generalintent

import android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT_TREE

fun toFile(context: Context) {
    val intent: Intent = Intent()
//        intent.action = ACTION_GET_CONTENT
//        intent.action = ACTION_OPEN_DOCUMENT
//        intent.action = ACTION_CREATE_DOCUMENT
//        intent.type = "*/*"
    intent.action = ACTION_OPEN_DOCUMENT_TREE
    // 上面三个一定要指定类型
    context.startActivity(intent)
}

// 任务管理器
// manifest，application 属性android:maxRecents 最大最近页签数量，<= 50 (低内存25
// 持久化android:persistableMode ，重启还能保持
fun flag(context: Context) {
    // 对应 manifest 中 intoExisting/always
//    val intent: Intent = Intent(this, FirstAct::class.java)
//    //单独作为一个栈，如果
//    intent.flags = FLAG_ACTIVITY_NEW_DOCUMENT
//    context.startActivity(intent)
}

//onProfileProvisioningComplete()
//setProfileEnabled()
//enableSystemApp()
//sample BasicManagedProfile
fun systemAdmin(context: Context) {
    val intent = Intent()
    intent.action = ACTION_PROVISION_MANAGED_PROFILE
    context.startActivity(intent)
}
