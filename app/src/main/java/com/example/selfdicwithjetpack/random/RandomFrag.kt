package com.example.selfdicwithjetpack.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.display.DisplayBean

/**
 * Created by TpOut on 2020/10/14.<br>
 * Email address: 416756910@qq.com<br>
 */

const val MAX_RANDOM_SIZE = 5

class RandomFrag : Fragment() {

    private var list: ArrayList<DisplayBean> = arrayListOf()
    private var mIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.random_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}