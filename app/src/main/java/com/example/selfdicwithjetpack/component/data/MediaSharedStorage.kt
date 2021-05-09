package com.example.selfdicwithjetpack.component.data

import android.app.Activity
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.blankj.utilcode.util.LogUtils
import com.example.selfdicwithjetpack.model.utils.storage.SharedStorage
import java.util.concurrent.TimeUnit

/**
 * Created by TpOut on 2021/5/8.<br>
 * Email address: 416756910@qq.com<br>
 *     MediaStore 中包括，
 *           Images(图像和截图) - 包含在DCIM、/Pictures - 对应MediaStore.Image 表
 *           Videos() - 包含在DCIM、/Movies、/Pictures - 对应MediaStore.Video 表
 *           Audio() -  Alarms/, Audiobooks/, Music/, Notifications/, Podcasts/, Ringtones/，Movies/ - 对应MediaStore.Audio 表
 *           Download - Download/ - 对应 MediaStore.Downloads 表
 *           scope 范围内文件 - 对应 MediaStore.Files
 *
 *     为了方便，可以直接使用File / fopen() 方法，和专有目录及Media 中的scoped 目录中的文件交互
 *     如果有权限不足的，会抛出 FileNotFoundException
 *     因为android 10 专有的scoped， 建议此时设置 requestLegacyExternalStorage = true
 *
 */
class MediaSharedStorage: SharedStorage {

    fun getVolume(context: Context) {
        val volumeNames: Set<String> = MediaStore.getExternalVolumeNames(context)
        val firstVolumeName = volumeNames.iterator().next()
    }

    // ACCESS_MEDIA_LOCATION
    fun queryPhotoLocation(context: Context, uri: Uri) {
        val photoUri = MediaStore.setRequireOriginal(uri)
        context.contentResolver.openInputStream(photoUri)?.use { stream ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                ExifInterface(stream).run {
                    // If lat/long is null, fall back to the coordinates (0, 0)
                    val latLong = this.getLatLong(floatArrayOf(0.0f, 0.0f))
                }
            } else {
                TODO("VERSION.SDK_INT < N")
            }
        }
    }

    fun queryVideoLocation(context: Context, uri: Uri) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)
        } catch (e: RuntimeException) {
            LogUtils.e("屠龙宝刀", "Cannot retrieve video file", e)
        }
        // Metadata should use a standardized format.
        val locationMetadata: String? =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION)
    }

    val REQUEST_CODE_TO_WRITE = 2

    // Need the READ_EXTERNAL_STORAGE permission if accessing video files that your
// app didn't create.
    fun query(context: Activity) {
        val videoList = mutableListOf<Video>()
        val videoUriList = mutableListOf<Uri>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL // 只读
//                            VOLUME_EXTERNAL_PRIMARY // 读写
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        // RELATIVE_PATH
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

// Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES).toString()
        )

// Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

//                var photoUri: Uri = Uri.withAppendedPath(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    cursor.getString(idColumnIndex)
//                )
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                videoUriList += contentUri
                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(contentUri, name, duration, size)
            }

            val pendingIntent =
                MediaStore.createWriteRequest(context.contentResolver, videoUriList) // 请求写权限
//            MediaStore.createFavoriteRequest() // 收藏
//            createTrashRequest
//            createDeleteRequest
            // Launch a system prompt requesting user permission for the operation.
            startIntentSenderForResult(
                context, pendingIntent.intentSender, REQUEST_CODE_TO_WRITE,
                null, 0, 0, 0, null
            )
            // onActivityResult
        }
    }

    // 可以用glide 代替？
    fun queryThumb(context: Context, uri: Uri) {
        val thumbnail: Bitmap =
            context.contentResolver.loadThumbnail(uri, Size(640, 480), null)
    }

    // Open a specific media item using ParcelFileDescriptor.
    fun openDescriptor(context: Context, uri: Uri) {
        val resolver = context.contentResolver
// "rw" for read-and-write;
// "rwt" for truncating or overwriting existing file contents.
        val readOnlyMode = "r"
        resolver.openFileDescriptor(uri, readOnlyMode).use { pfd ->
            // Perform operations on "pfd".
        }
    }

    fun openStream(context: Context, uri: Uri) {
        val resolver = context.contentResolver
        resolver.openInputStream(uri).use { stream ->
            // Perform operations on "stream".
        }
    }

    // Add a specific media item.
    fun insert(context: Context, filename: String) {
        val resolver = context.contentResolver

        val audioCollection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        val newSongDetails = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, filename)
            put(MediaStore.Audio.Media.IS_PENDING, 1) // 处理过程中，对其他app 不可见
//            put(MediaStore.Audio.Media.RELATIVE_PATH, "") // 设置到非默认文件夹
        }

        val newSongUri = resolver.insert(audioCollection, newSongDetails)
        newSongUri?.let {
            resolver.openFileDescriptor(newSongUri, "w", null).use { pfd ->
                // Write data into the pending audio file.
            }
            // Now that we're finished, release the "pending" status, and allow other apps
// to play the audio track.
            newSongDetails.clear()
            newSongDetails.put(MediaStore.Audio.Media.IS_PENDING, 0) // 处理结束，对其他app 可见
            resolver.update(newSongUri, newSongDetails, null, null)
        }
    }

    fun update(context: Context, uri: Uri) {
        val resolver = context.contentResolver
//
//// When performing a single item update, prefer using the ID
//        val selection = "${MediaStore.Audio.Media._ID} = ?"
//
//// By using selection + args we protect against improper escaping of // values.
//        val selectionArgs = arrayOf(mediaId.toString())

        val updatedSongDetails = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, "My Favorite Song.mp3")
        }

        val numSongsUpdated = resolver.update(
            uri,
            updatedSongDetails,
//            selection,
            null,
//            selectionArgs
            null
        )
    }

    val REQUEST_CODE = 1

    // 没有权限的情况
    fun updateOutOfScoped(context: Activity, uri: Uri) {
        try {
            context.contentResolver.openFileDescriptor(uri, "w")?.use {
//                setGrayscaleFilter(it)
            }
        } catch (securityException: SecurityException) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val recoverableSecurityException = securityException as?
                        RecoverableSecurityException
                    ?: throw RuntimeException(securityException.message, securityException)

                val intentSender =
                    recoverableSecurityException.userAction.actionIntent.intentSender
                intentSender?.let {
                    startIntentSenderForResult(
                        context, intentSender, REQUEST_CODE,
                        null, 0, 0, 0, null
                    )
                }
            } else {
                throw RuntimeException(securityException.message, securityException)
            }
        }
    }

    fun passToNative(context: Context, cursor: Cursor) {
        val contentUri: Uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID).toLong()
        )
        val fileOpenMode = "r"
        val parcelFd = context.contentResolver.openFileDescriptor(contentUri, fileOpenMode)
        val fd = parcelFd?.detachFd()
// Pass the integer value "fd" into your native code. Remember to call
// close(2) on the file descriptor when you're done using it.
    }

    // 如果有异常的话，也得参看 updateOutOfScoped
    fun remove(context: Context, uri: Uri) {
        val resolver = context.contentResolver

        val selection = "..."
        val selectionArgs = "..."

        val numImagesRemoved = resolver.delete(
            uri,
            selection,
            null
        )
    }

}

// Container for information about each video.
data class Video(
    val uri: Uri,
    val name: String,
    val duration: Int,
    val size: Int
)