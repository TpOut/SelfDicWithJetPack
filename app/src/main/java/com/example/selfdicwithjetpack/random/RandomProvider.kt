package com.example.selfdicwithjetpack.random

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import com.example.selfdicwithjetpack.MainActivity
import com.example.selfdicwithjetpack.R

/**
 * Created by TpOut on 2021/5/20.<br>
 * Email address: 416756910@qq.com<br>
 */
class RandomWidgetProvider : AppWidgetProvider(){


    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach { appWidgetId ->
            // 点击事件
            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }
            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.random_widget
            ).apply {
                setOnClickPendingIntent(R.id.tv, pendingIntent)
            }

            // 更新
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }


    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}