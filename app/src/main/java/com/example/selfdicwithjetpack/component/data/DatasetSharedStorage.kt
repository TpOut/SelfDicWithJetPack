package com.example.selfdicwithjetpack.component.data

import android.app.blob.BlobHandle
import android.app.blob.BlobStoreManager
import android.content.Context
import android.content.Context.BLOB_STORE_SERVICE
import android.os.ParcelFileDescriptor
import com.example.selfdicwithjetpack.model.utils.storage.FileStorage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


class DatasetSharedStorage : FileStorage {

    fun getBlobHandle(sha256DigestBytes: ByteArray): BlobHandle {
        return BlobHandle.createWithSha256(
            sha256DigestBytes,
            "Sample photos",
            System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1),
            "photoTrainingDataset"
        )
    }

    fun access(context: Context, sha256DigestBytes: ByteArray) {
        val blobStoreManager = context.getSystemService(BLOB_STORE_SERVICE) as BlobStoreManager
        ParcelFileDescriptor.AutoCloseInputStream(
            blobStoreManager.openBlob(getBlobHandle(sha256DigestBytes))
        ).use { inputStream ->
            // useData(inputStream);
        }
    }

    fun write(context: Context, sha256DigestBytes: ByteArray) {
        val blobStoreManager = context.getSystemService(BLOB_STORE_SERVICE) as BlobStoreManager
        // sessionId 可以被复用，即使设备重启，按目前的理解，是可以上传到服务器，其他app 就可以用来共享了
        val sessionId: Long = blobStoreManager.createSession(getBlobHandle(sha256DigestBytes))
        blobStoreManager.openSession(sessionId).use { session ->
            ParcelFileDescriptor.AutoCloseOutputStream(
                // 写入在文件开始位置，写入200m
                session.openWrite(0, 1024 * 1024 * 200)
            ).use { outStream ->
//                writeData(outStream)
            }
            session.allowSameSignatureAccess()
//            session.allowPackageAccess("packageName", certificate)
            // allowPublicAccess
            session.commit(Executors.newSingleThreadExecutor(), Consumer<Int> {

            })
        }

    }
}