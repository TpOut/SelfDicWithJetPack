package com.example.selfdicwithjetpack.component.crypto

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

/**
 * 二进制转十六进制
 *
 * @param bytes
 * @return
 */
fun bytesToHex(bytes: ByteArray): String? {
    val md5str = StringBuffer()
    // 把数组每一字节换成16进制连成md5字符串
    var digital: Int
    for (i in bytes.indices) {
        digital = bytes[i].toInt()
        if (digital < 0) {
            digital += 256
        }
        if (digital < 16) {
            md5str.append("0")
        }
        md5str.append(Integer.toHexString(digital))
    }
    return md5str.toString().toUpperCase()
}
