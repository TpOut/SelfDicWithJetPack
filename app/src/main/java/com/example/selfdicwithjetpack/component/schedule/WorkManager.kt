package com.example.selfdicwithjetpack.component.schedule

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import androidx.work.impl.model.WorkProgress
import com.example.selfdicwithjetpack.component.schedule.UploadSyncWorker.Companion.Progress
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * Created by TpOut on 2021/5/27.<br>
 * Email address: 416756910@qq.com<br>
 */

// ListenableWorker
fun singleWork(context: Context) {
    WorkManager
        .getInstance(context)
        // 唯一任务
//        .enqueueUniquePeriodicWork()
        .enqueue(
            // 循环任务
//            PeriodicWorkRequestBuilder<UploadWorker>(
//                1, TimeUnit.HOURS,
//                15, TimeUnit.MINUTES
//            )
            OneTimeWorkRequestBuilder<UploadWorker>()
                // 启动条件
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .setRequiresCharging(true)
                        .build()
                )
                // 条件达到时，启动延迟
                .setInitialDelay(
                    10, TimeUnit.MINUTES
                )
                // 返回Result.retry() 时，重试策略
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                // 通过标签管理
                .addTag("sample")
                // 初始化参数
                .setInputData(
                    workDataOf("image" to "http.sample.com")
                )
                .build()
        )
}

fun multiWork(context: Context){
    val work1 = OneTimeWorkRequestBuilder<UploadWorker>()
        .setInputMerger(ArrayCreatingInputMerger::class) // 指定冲突合并
        .build()
    val work2 = OneTimeWorkRequestBuilder<UploadWorker>().build()
    val work3 = OneTimeWorkRequestBuilder<UploadWorker>().build()
    WorkManager.getInstance(context)
        // Candidates to run in parallel
        .beginWith(listOf(work1, work2))
        // Dependent work (only runs after all previous work in chain)
        .then(work3)
        // Call enqueue to kick things off
        .enqueue()

}

fun getWorkInfo(context: Context, viewLifecycleOwner: LifecycleOwner) {
    WorkManager
        .getInstance(context)
//        .getWorkInfoById(UUID.randomUUID())
        // 复杂查询
//        .getWorkInfos(
//            WorkQuery.Builder
//                .fromTags(listOf("syncTag"))
//                .addStates(listOf(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
//                .addUniqueWorkNames(listOf("preProcess", "sync")
//                )
//                .build()
//        )
        // liveData
        .getWorkInfosByTagLiveData("sample")
        .observe(
            viewLifecycleOwner,
            Observer<MutableList<WorkInfo>> {
                if (it.size > 1) {
                    if (it[0].state == WorkInfo.State.SUCCEEDED) {
                        print("嘻嘻")
                    }
                    it[0].progress.getInt(Progress, 0)
                }
            }

        )

}

// 还支持RxJava 形式
class UploadSyncWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 1L
    }

    override suspend fun doWork(): Result {
        val firstUpdate = workDataOf(Progress to 0)
        val lastUpdate = workDataOf(Progress to 100)
        setProgress(firstUpdate)
        delay(delayDuration)
        setProgress(lastUpdate)
        return Result.success()
    }
}

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // 获取参数
//        inputData.getString()

        for (i in 1..10) {
//            setProgressAsync()
            Log.d("屠龙宝刀","progress $i")
            print("uploading... progress: ${i * 10}")
        }
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
    }
}