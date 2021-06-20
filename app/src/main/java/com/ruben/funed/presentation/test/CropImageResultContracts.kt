package com.ruben.funed.presentation.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.ruben.funed.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageOptions
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * Created by ruben.quadros on 20/06/21.
 **/
class CropImageResultContracts: ActivityResultContract<Uri, Uri?>() {

    override fun createIntent(context: Context, input: Uri?): Intent {
        val intent = Intent(context, CropImageActivity::class.java)
        val bundle = Bundle()
        val cropImageOptions = CropImageOptions()
        cropImageOptions.guidelines = CropImageView.Guidelines.ON
        cropImageOptions.cropShape = CropImageView.CropShape.RECTANGLE
        cropImageOptions.cropMenuCropButtonIcon = R.drawable.ic_check
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, input)
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, cropImageOptions)
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if(resultCode != Activity.RESULT_OK) {
            return null
        }
        val result = CropImage.getActivityResult(intent)
        return result.uri
    }
}