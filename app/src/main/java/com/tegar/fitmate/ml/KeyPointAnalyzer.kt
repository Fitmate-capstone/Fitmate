package com.tegar.fitmate.ml

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.platform.LocalContext
import com.tegar.fitmate.ui.model.Person
import com.tegar.fitmate.ui.tracker.YuvToRgbConverter


class KeyPointAnalyzer(
    private val context: Context,
    private val classifier: PoseDetector,
    private val onResult: (List<Person>) -> Unit,

) : ImageAnalysis.Analyzer {

    private val yuvToRgbConverter = YuvToRgbConverter(context)

    @OptIn(ExperimentalGetImage::class) override fun analyze(image: ImageProxy ) {


        // Convert ImageProxy to Bitmap in RGB format
        val rgbBitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        yuvToRgbConverter.yuvToRgb(image.image!!, rgbBitmap)

        // Estimate poses
        val result = classifier.estimatePoses(rgbBitmap)
        onResult(result)

        // Close the image to avoid memory leaks
        image.close()

    }
}
