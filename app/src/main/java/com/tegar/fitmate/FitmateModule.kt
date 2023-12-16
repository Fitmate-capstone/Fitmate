package com.tegar.fitmate

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.tegar.fitmate.data.local.dao.SchenduleExerciseDao
import com.tegar.fitmate.data.local.database.FitmateDatabase
import com.tegar.fitmate.data.model.ApiConstant
import com.tegar.fitmate.data.remote.retrofit.MlApiService
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FitmateModule {

    @Singleton
    @Provides
    fun getRepository(dao : SchenduleExerciseDao) : SchenduleExerciseRepository{
        return SchenduleExerciseRepository(dao)
    }

    @Singleton
    @Provides
    fun getDao(database : FitmateDatabase) : SchenduleExerciseDao {
        return database.schenduleDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FitmateDatabase {
        return Room.databaseBuilder(
            context.applicationContext, FitmateDatabase::class.java, "fitmate"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideMlApi(builder: Retrofit.Builder): MlApiService{
        return builder
            .build()
            .create(MlApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        val client = clientBuilder.build()


        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstant.ML_BASE_URL)
            .client(client)


    }
}