package dev.aayushgupta.recipecookbook.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

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