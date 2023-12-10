package com.tegar.fitmate.ui.screens.equimentsearch

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EquimentSearchViewModel : ViewModel() {


    fun onTakePhoto(uri : Uri) {
        Log.d("Bitmap", uri.toString())
    }
}