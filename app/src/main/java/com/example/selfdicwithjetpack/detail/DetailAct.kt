package com.example.selfdicwithjetpack.detail

import android.os.Bundle
import android.view.DragEvent.ACTION_DROP
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.selfdicwithjetpack.R

/**
 * Created by TpOut on 2021/4/23.<br>
 * Email address: 416756910@qq.com<br>
 */
class DetailAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_act)

//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add<ExampleFragment>(R.id.fragment_container_view, args = bundle)
//        }

        findViewById<TextView>(R.id.tv).setOnDragListener { v, event ->
            when (event.action) {
                ACTION_DROP -> {
                    (v as TextView).text = event.clipData.getItemAt(0).intent.let {
                        it.getStringExtra("src") + "\n" + it.getStringExtra("dst") + "\n" + it.getStringExtra("sentence")
                    }
                }
            }
            return@setOnDragListener true
        }
    }

}