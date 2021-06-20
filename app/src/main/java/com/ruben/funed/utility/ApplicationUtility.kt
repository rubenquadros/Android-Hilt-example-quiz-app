package com.ruben.funed.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ruben.quadros on 20/06/21.
 **/
object ApplicationUtility {

    fun createImageFile(context: Context): File {
        val timeStamp =
            SimpleDateFormat(ApplicationConstants.DATE_FORMAT, Locale.getDefault()).format(Date())
        val imageFileName =
            ApplicationConstants.IMAGE_PREFIX + timeStamp + ApplicationConstants.IMAGE_SUFFIX
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ApplicationConstants.JPG_SUFFIX, storageDir)
    }

    @Suppress("DEPRECATION")
    fun getResizedImage(context: Context, image: Uri): Uri {
        var bitmap: Bitmap
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, image)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, image)
        }
        return if(bitmap.byteCount <= ApplicationConstants.IMAGE_BYTE_COUNT) {
            image
        }else {
            val storageDir = context.getExternalFilesDir(DIRECTORY_PICTURES)
            val file = File.createTempFile(ApplicationConstants.RESIZED_IMAGE, ApplicationConstants.JPG_SUFFIX, storageDir)
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()
            bitmap = BitmapFactory.Options().run {
                inJustDecodeBounds = true
                inSampleSize = calculateInSampleSize(this)
                inJustDecodeBounds = false
                BitmapFactory.decodeFile(file.absolutePath, this)
            }
            val reducedFos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, reducedFos)
            reducedFos.flush()
            reducedFos.close()
            bitmap.recycle()
            Uri.fromFile(file)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > ApplicationConstants.IMAGE_HEIGHT || width > ApplicationConstants.IMAGE_WIDTH) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= ApplicationConstants.IMAGE_HEIGHT && halfWidth / inSampleSize >= ApplicationConstants.IMAGE_WIDTH) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}