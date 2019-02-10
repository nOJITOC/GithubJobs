package com.mmteams91.githubjobs.common.data.network

import com.mmteams91.githubjobs.BuildConfig
import com.mmteams91.githubjobs.features.job.data.JobsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RestModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
            .build()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(ApplicationJsonAdapterFactory.INSTANCE)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL)
            .build()
    @Provides
    @Singleton
    fun provideJobsApi(retrofit: Retrofit):JobsApi = retrofit.create(JobsApi::class.java)


}