package com.example.selfdicwithjetpack.display

import android.app.Dialog
import android.os.Bundle
import com.example.selfdicwithjetpack.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by TpOut on 2021/4/28.<br>
 * Email address: 416756910@qq.com<br>
 */
class DicListFrag : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.dic_list_frag)
        return dialog
    }
}