package com.tegar.fitmate.data.remote.retrofit

import com.tegar.fitmate.data.remote.model.DetailExerciseRespone
import com.tegar.fitmate.data.remote.model.ExerciseResponse
import com.tegar.fitmate.data.remote.model.MuscleResponse
import com.tegar.fitmate.data.remote.model.PredictEquimentResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("/getMuscle")
    suspend fun getMuscleList(

    ): MuscleResponse


    @GET("/getExercise")
    suspend fun getExerciseList(
        @Query("muscle_id") id: Int
    ): ExerciseResponse

    @GET("/getDetailExercise")
    suspend fun getDetailExercise(
        @Query("exercise_id") id: Int
    ): DetailExerciseRespone

    @GET("/getTopRatedExercise")
    suspend fun getTopRatedExercise(
        @Query("limit") id: Int = 5
    ): ExerciseResponse

    @GET("/getExerciseByQuery")
    suspend fun searchExercise(
        @Query("query") query: String
    ): ExerciseResponse
}