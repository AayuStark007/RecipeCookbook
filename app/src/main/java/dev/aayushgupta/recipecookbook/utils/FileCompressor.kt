package dev.aayushgupta.recipecookbook.utils

import android.graphics.Bitmap
import java.io.File
import java.io.IOException

class FileCompressor @JvmOverloads constructor(
    var maxWidth: Int = 512,
    var maxHeight: Int = 512,
    var compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    var quality: Int = 80,
    var destPath: String
) {

    @Throws(IOException::class)
    fun compressToFile(imageFile: File): File {
        return compressToFile(imageFile, imageFile.name)
    }

    @Throws(IOException::class)
    private fun compressToFile(imageFile: File, compressedFileName: String): File {
        return ImageUtils.compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality,
            "${destPath}${File.separator}${compressedFileName}")
    }

    // TODO compressToFile(...): Flow<File>

}