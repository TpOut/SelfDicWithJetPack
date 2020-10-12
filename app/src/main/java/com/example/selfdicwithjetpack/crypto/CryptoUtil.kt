package com.example.selfdicwithjetpack.crypto

import java.security.MessageDigest

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

/**
 * 生成md5
 *
 * @param message
 * @return
 */
fun getMD5(message: String): String? {
    var md5str: String? = ""
    try {
        // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
        val md = MessageDigest.getInstance("MD5")

        // 2 将消息变成byte数组
        val input = message.toByteArray()

        // 3 计算后获得字节数组,这就是那128位了
        val buff = md.digest(input)

        // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
        md5str = bytesToHex(buff)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return md5str
}