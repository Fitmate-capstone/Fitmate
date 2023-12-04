package com.tegar.fitmate.ml

import android.graphics.Bitmap
import com.tegar.fitmate.ui.model.Person


interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}
