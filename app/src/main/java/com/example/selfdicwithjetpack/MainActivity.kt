package com.example.selfdicwithjetpack

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.display.DISPLAY_FRAG_TAG
import com.example.selfdicwithjetpack.display.DisplayViewModel

const val MAIN_ACTIVITY_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    val mViewMode by viewModels<DisplayViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        setSupportActionBar(findViewById(R.id.toolbar))

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        LogUtils.d(MAIN_ACTIVITY_TAG, "onPictureInPictureModeChanged : $isInPictureInPictureMode --")
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
}