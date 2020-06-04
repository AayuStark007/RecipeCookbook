package dev.aayushgupta.recipecookbook.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageUtils {

    @Throws(IOException::class)
    fun compressImage(
        imageFile: File, reqW: Int, reqH: Int,
        compressFormat: Bitmap.CompressFormat,
        quality: Int, destPath: String
    ): File {
        File(destPath).parentFile?.also { file ->
            if (!file.exists()) file.mkdirs()
            FileOutputStream(destPath).also { foutStream ->
                try {
                    // write the compressed bitmap to the dest specified by destPath
                    decodeSampledBitmapFromFile(imageFile, reqW, reqH)
                        .compress(compressFormat, quality, foutStream)
                } finally {
                    foutStream.flush()
                    foutStream.close()
                }
            }
        }
        return File(destPath)
    }

    @Throws(IOException::class)
    fun decodeSampledBitmapFromFile(imageFile: File, reqW: Int, reqH: Int): Bitmap {
        BitmapFactory.Options().also { options ->
            // First decode with inJustDecodeBounds=true to check dimensions
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, options)

            options.inSampleSize = calculateInSampleSize(options, reqW, reqH)
            options.inJustDecodeBounds = false

            // Decode again with inSampleSize set
            val scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)
            // fix rotation
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix().also {
                when(orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> it.postRotate(90F)
                    ExifInterface.ORIENTATION_ROTATE_180 -> it.postRotate(180F)
                    ExifInterface.ORIENTATION_ROTATE_270 -> it.postRotate(270F)
                    else -> Timber.d("Unknown orientation: $orientation")
                }
            }
            return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqW: Int, reqH: Int): Int {
        // Raw width and height of image
        val h = options.outHeight
        val w = options.outWidth
        var sampleSize = 1

        if (h > reqH || w > reqW) {
            val halfH = h / 2
            val halfW = w / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfH / sampleSize) >= reqH && (halfW / sampleSize) >= reqW) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }
}