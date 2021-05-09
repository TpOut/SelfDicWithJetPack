package com.example.selfdicwithjetpack.general

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.selfdicwithjetpack.component.data.MmkvStorage

/**
 * Created by TpOut on 2021/4/27.<br>
 * Email address: 416756910@qq.com<br>
 */
class PrivacyDialog : DialogFragment() {

    val storage = MmkvStorage()

    //直接使用弹窗，互斥onCreateView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("隐私弹窗")
            .setMessage("这是隐私弹窗内容哦")
            .setPositiveButton("好的") { _, _ ->
                storage.store(KEY_STORAGE_PRIVATE_DIALOG_SHOW, true)
            }
            .setNegativeButton("不好") { _, _ ->
                requireActivity().finish()
            }
            .create()

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }

    // 自定义view，互斥onCreateDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, tag)
    }

    companion object {
        const val TAG = "PrivacyDialog"
        const val KEY_STORAGE_PRIVATE_DIALOG_SHOW = "KEY_STORAGE_PRIVATE_DIALOG_SHOW"
    }
}