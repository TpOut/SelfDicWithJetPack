package com.example.selfdicwithjetpack

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.selfdicwithjetpack.component.data.MmkvStorage
import com.example.selfdicwithjetpack.component.schedule.singleWork
import com.example.selfdicwithjetpack.display.DisplayViewModel
import com.example.selfdicwithjetpack.general.PrivacyDialog
import com.example.selfdicwithjetpack.random.RandomWidgetProvider

const val MAIN_ACTIVITY_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    val mViewMode by viewModels<DisplayViewModel>()
    val storage = MmkvStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (!storage.hasKey(PrivacyDialog.KEY_STORAGE_PRIVATE_DIALOG_SHOW)) {
            PrivacyDialog().show(supportFragmentManager)
        }
        singleWork(this)
//        rvHistory.scrollToPosition(0)
//        btnConfirm.setEnabled(false)
//        val src: String = transResult.getSrc()
//        val dst: String = transResult.getDst()
//        val itemBean = ItemBean(src, dst, "")
//        if (!TextUtils.isEmpty(currentSentence)) {
//            itemBean.setSentence(currentSentence)
//        }
//        LogUtil.d(MainActivity.TAG, "" + itemBean)
//        itemBean.save()
//        AddWordHelper.upload(itemBean.getQuerySrc(), itemBean.getQueryResult(), itemBean.getSentence())
//        beanList.add(0, itemBean)
//        LogUtil.d(MainActivity.TAG, "" + beanList)
//        historyAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        addRandomWidget()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                ToastUtils.showShort("你好，主页")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // multi-resume 情况下，要处理麦克风、摄像头等
    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        LogUtils.d(
            MAIN_ACTIVITY_TAG,
            "onPictureInPictureModeChanged : $isInPictureInPictureMode --"
        )
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        LogUtils.d(MAIN_ACTIVITY_TAG, "onPictureInPictureModeChanged : $isInPictureInPictureMode")
    }

    // true 的时候，是在onConfigurationChanged 之前调
    // false 的时候，是在onConfigurationChanged 之后一段时间调。小米mix2 手机
    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration?) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
        LogUtils.d(MAIN_ACTIVITY_TAG, "onMultiWindowModeChanged : $isInMultiWindowMode --")
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
        LogUtils.d(MAIN_ACTIVITY_TAG, "onMultiWindowModeChanged : $isInMultiWindowMode")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.d(MAIN_ACTIVITY_TAG, "onConfigurationChanged : $newConfig")
    }

    private fun addRandomWidget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appWidgetManager: AppWidgetManager = getSystemService(AppWidgetManager::class.java)
            val myProvider = ComponentName(this, RandomWidgetProvider::class.java)

            val successCallback: PendingIntent? =
                if (appWidgetManager.isRequestPinAppWidgetSupported) {
                    // Create the PendingIntent object only if your app needs to be notified
                    // that the user allowed the widget to be pinned. Note that, if the pinning
                    // operation fails, your app isn't notified.
                    Intent().let { intent ->
                        // Configure the intent so that your app's broadcast receiver gets
                        // the callback successfully. This callback receives the ID of the
                        // newly-pinned widget (EXTRA_APPWIDGET_ID).
                        PendingIntent.getBroadcast(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    }
                } else {
                    null
                }

            successCallback?.also { pendingIntent ->
                appWidgetManager.requestPinAppWidget(myProvider, null, pendingIntent)
            }
        }

    }
}