package com.example.selfdicwithjetpack.main

import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.selfdicwithjetpack.R

class MainActivity : AppCompatActivity() {

    val mViewMode by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }




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
}