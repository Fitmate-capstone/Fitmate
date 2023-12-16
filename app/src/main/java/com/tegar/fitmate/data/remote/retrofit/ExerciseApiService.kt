package com.tegar.fitmate.data.remote.retrofit

import com.tegar.fitmate.data.remote.model.MuscleResponse
import com.tegar.fitmate.data.remote.model.PredictEquimentResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ExerciseApiService {

    @GET("/getMuscle")
    suspend fun getMuscleList(

    ): MuscleResponse
}