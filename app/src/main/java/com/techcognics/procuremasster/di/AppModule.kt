package com.techcognics.procuremasster.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.techcognics.procuremasster.data.local.SessionManager
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.AuthInterceptor
import com.techcognics.procuremasster.data.remote.SafeDoubleAdapter
import com.techcognics.procuremasster.data.remote.SafeIntAdapter
import com.techcognics.procuremasster.data.repository.AuctionRepositoryImpl
import com.techcognics.procuremasster.data.repository.AuthRepositoryImpl
import com.techcognics.procuremasster.data.repository.RfqRepositoryImpl
import com.techcognics.procuremasster.domain.repository.AuctionRepository
import com.techcognics.procuremasster.domain.repository.AuthRepository
import com.techcognics.procuremasster.domain.repository.RfqRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://ec2-13-48-134-83.eu-north-1.compute.amazonaws.com:8080/api/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Double::class.java, SafeDoubleAdapter())
            .registerTypeAdapter(Int::class.java, SafeIntAdapter())
            .create()
    }
    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(sessionManager: SessionManager): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager)) // ✅ Token Interceptor
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)



    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiService, sessionManager: SessionManager): AuthRepository =
        AuthRepositoryImpl(api, sessionManager)

    @Provides
    @Singleton
    fun provideRfqRepository(api: ApiService): RfqRepository {
      return  RfqRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuctionRepository(api: ApiService): AuctionRepository{
        return AuctionRepositoryImpl(api)
    }


}
