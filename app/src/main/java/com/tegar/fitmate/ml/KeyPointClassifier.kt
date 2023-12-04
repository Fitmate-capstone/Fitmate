package com.tegar.fitmate.ml

import android.app.Person
import android.graphics.Bitmap

interface  KeyPointClassifier {
    fun classify(bitmap : Bitmap, rotation : Int) : List<Person>

}