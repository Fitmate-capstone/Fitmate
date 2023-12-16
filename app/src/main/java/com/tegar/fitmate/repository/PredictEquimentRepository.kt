package com.tegar.fitmate.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.tegar.fitmate.data.remote.model.PredictEquimentResponse
import com.tegar.fitmate.data.remote.retrofit.MlApiService
import com.tegar.fitmate.data.util.UiState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.converter.moshi.MoshiConverterFactory

class PredictEquimentRepository @Inject constructor(private val mlApiService: MlApiService) {



    fun predict(image : File)  : Flow<UiState<PredictEquimentResponse>> = flow {
        emit(UiState.Loading)
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            image.name,
            requestImageFile
        )

        try{
            val predictImageRespnse = mlApiService.predictImage(multipartBody)

            Log.d("WHEN PRDICT IMAGE " , predictImageRespnse.toString())
            emit(UiState.Success(predictImageRespnse))

        } catch (e: HttpException) {


            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PredictEquimentResponse::class.java)

            errorResponse.status?.message?.let { UiState.Error(it) }?.let { emit(it) }
        }
    }

}