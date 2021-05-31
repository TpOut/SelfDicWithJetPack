package com.example.selfdicwithjetpack.component.schedule

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by TpOut on 2021/5/27.<br>
 * Email address: 416756910@qq.com<br>
 */
class WorkManager{




}

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
//        uploadImages()
        return Result.success()
    }
}