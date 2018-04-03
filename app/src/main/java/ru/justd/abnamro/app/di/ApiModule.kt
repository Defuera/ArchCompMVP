package ru.justd.abnamro.app.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.justd.abnamro.BuildConfig
import ru.justd.abnamro.app.model.api.VenueApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {

    companion object {
        private const val PARAM_VERSION = "v"
        private const val PARAM_TOKEN = "oauth_token"
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request()
                    val url = request.url()
                            .newBuilder()
                            .addQueryParameter(PARAM_VERSION, "20180401")
                            .addQueryParameter(PARAM_TOKEN, BuildConfig.TOKEN)
                            .build()


                    val newRequest = request.newBuilder().url(url).build()
                    val response = chain.proceed(newRequest)
                    response
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.BASIC)
                )
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
    }

    @Provides
    @Singleton
    internal fun provideApiService(builder: Retrofit.Builder): VenueApi {
        return builder
                .baseUrl("https://api.foursquare.com/v2/")
                .build()
                .create(VenueApi::class.java)
    }

}