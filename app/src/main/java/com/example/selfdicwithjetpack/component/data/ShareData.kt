package com.example.selfdicwithjetpack.component.data

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_CHOSEN_COMPONENT
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Created by TpOut on 2021/5/10.<br>
 * Email address: 416756910@qq.com<br>
 */
class ShareData {

    companion object {
        const val SHARE_RESULT_REQUEST_CODE = 1

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        class ShareResultBroadcastReceiver : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val clickedComponent: ComponentName? =
                    intent?.getParcelableExtra(EXTRA_CHOSEN_COMPONENT);
            }
        }
    }

    fun shareOneToOther(context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            putExtra(Intent.EXTRA_TITLE, "share text") // 提示
//            ClipData // 缩略图
            addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            type = "text/plain"
//            putExtra(Intent.EXTRA_STREAM, uriToImage)
//            type = "image/jpeg"
        }

        val pi = PendingIntent.getBroadcast(
            context, SHARE_RESULT_REQUEST_CODE,
            Intent(context, ShareResultBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 这种方式，会打开一个SharedSheet
        // 如果不调用createChooser，直接startActivity，则叫做intentResolver（如果有多个接收应用，则系统打开一个选择弹窗
        val shareIntent =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                Intent.createChooser(sendIntent, null, pi.intentSender)
            } else {
                Intent.createChooser(sendIntent, null)
            }
        context.startActivity(shareIntent)
    }

    fun shareMultipleToOther(context: Context) {
        val imageUris: ArrayList<Uri> = arrayListOf(
            // Add your image URIs here
//            imageUri1,
//            imageUri2
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            type = "image/*"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share images to.."))
    }


}