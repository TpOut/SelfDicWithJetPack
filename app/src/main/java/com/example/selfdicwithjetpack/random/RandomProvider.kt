package com.example.selfdicwithjetpack.random

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle

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