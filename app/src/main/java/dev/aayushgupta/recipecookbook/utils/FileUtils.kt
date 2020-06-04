package dev.aayushgupta.recipecookbook.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SimpleDateFormat")
@Throws(IOException::class)
fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        ?: throw IOException("Failed to get image storage path")
    return File.createTempFile(
        "CB_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

fun Uri.getRealPathFromUri(context: Context): String {
    var cursor: Cursor? = null
    try {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(this, proj, null, null, null)?.also {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return ""
}