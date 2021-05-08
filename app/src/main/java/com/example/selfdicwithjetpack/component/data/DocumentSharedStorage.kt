package com.example.selfdicwithjetpack.component.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.selfdicwithjetpack.model.utils.storage.FileStorage
import java.io.*

/**
 * Created by TpOut on 2021/5/8.<br>
 * Email address: 416756910@qq.com<br>
 */
class DocumentSharedStorage : FileStorage {

    val REQUEST_CODE_CREATE_FILE = 1
    fun create(initialUri: Uri, context: Activity) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            }
        }
        context.startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)
    }

    fun open(initialUri: Uri, context: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            }
        }
        context.startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)
    }

    // 11 之后有限制
    // https://developer.android.google.cn/training/data-storage/shared/documents-files#document-tree-access-restrictions
    fun access(initialUri: Uri, context: Activity) {
// Choose a directory using the system's file picker.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker when it loads.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            }
        }

        context.startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)
    }

    fun accessForever(uri: Uri, context: Activity) {
        val contentResolver = context.contentResolver

        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    fun checkFlag() {
//        Document.COLUMN_FLAGS.
    }

    fun getMetaData(context: Context, uri: Uri) {
        val cursor: Cursor? = context.contentResolver.query(
            uri, null, null, null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i("屠龙宝刀", "Display Name: $displayName")

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                val size: String = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                Log.i("屠龙宝刀", "Size: $size")
            }
        }
    }

    // 讲道理一般也就不写这了吧
    @WorkerThread
    fun getBitmap(uri: Uri, context: Context): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            context.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }


    fun read(uri: Uri, context: Context): String {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }

    // 需要checkFlag 有FLAG_SUPPORTS_WRITE
    fun write(uri: Uri, context: Context) {
        try {
            context.contentResolver.openFileDescriptor(uri, "w")?.use { pfd ->
                FileOutputStream(pfd.fileDescriptor).use { fos ->
                    fos.write(
                        ("Overwritten at ${System.currentTimeMillis()}\n")
                            .toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 需要checkFlag 有 SUPPORTS_DELETE
    fun delete(uri: Uri, context: Context) {
        DocumentsContract.deleteDocument(context.contentResolver, uri)
    }

    // 7.0 之后有虚拟文件
    fun openVirtualFile(uri: Uri, context: Context, mimeTypeFilter: String): FileInputStream? {
        if (!isVirtualFile(uri, context)) {
            return null
        }
        val openableMimeTypes: Array<String>? =
            context.contentResolver.getStreamTypes(uri, mimeTypeFilter)

        return if (openableMimeTypes?.isNotEmpty() == true) {
            context.contentResolver
                .openTypedAssetFileDescriptor(uri, openableMimeTypes[0], null)
                ?.createInputStream()
        } else {
            null
        }
    }

    fun isVirtualFile(uri: Uri, context: Context): Boolean {
        if (!DocumentsContract.isDocumentUri(context, uri)) {
            return false
        }

        val cursor: Cursor? = context.contentResolver.query(
            uri,
            arrayOf(DocumentsContract.Document.COLUMN_FLAGS),
            null,
            null,
            null
        )

        val flags: Int = cursor?.use {
            if (cursor.moveToFirst()) {
                cursor.getInt(0)
            } else {
                0
            }
        } ?: 0

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            flags and DocumentsContract.Document.FLAG_VIRTUAL_DOCUMENT != 0
        } else {
            false
        }
    }

}