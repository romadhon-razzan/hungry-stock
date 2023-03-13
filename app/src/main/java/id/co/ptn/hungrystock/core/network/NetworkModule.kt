package id.co.ptn.hungrystock.core.network

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideBaseUrl() = ENV.serviceUrl()

    private fun getHeader(): Interceptor {

        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .addHeader("Accept","application/json")
                    .addHeader("Authorization", TOKEN)
                    .build()
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (ENV.debug()) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeader())
            .build()
    } else OkHttpClient
        .Builder()
        .addInterceptor(getHeader())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Services = retrofit.create(Services::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}