package com.example.selfdicwithjetpack.random

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.selfdicwithjetpack.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TpOut on 2021/5/20.<br>
 * Email address: 416756910@qq.com<br>
 */

const val RANDOM_CONFIG_ACT_TAG = "RandomConfigAct"

class RandomConfigAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_widget_config_act)

        Log.d(RANDOM_CONFIG_ACT_TAG, "is order: ${intent?.getBooleanExtra("order", false)}")

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        lifecycleScope.launch {
            delay(10_000)
            finishConfigure()
        }
    }

    private fun finishConfigure() {
        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this)
        RemoteViews(this.packageName, R.layout.random_widget).also { views ->
            appWidgetManager.updateAppWidget(R.id.tv, views)
        }
        val resultValue = Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, R.id.tv)
        }
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }

}