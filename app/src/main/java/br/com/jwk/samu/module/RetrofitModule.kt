package br.com.jwk.samu.module

import br.com.jwk.samu.BuildConfig
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "BASE_URL"
private const val GOOGLE_URL = "GOOGLE_URL"
private const val READ_TIME_OUT = "READ_TIME_OUT"
private const val CONNECT_TIME_OUT = "CONNECT_TIME_OUT"
private const val HEADER_INTERCEPTOR = "HEADER_INTERCEPTOR"
private const val LOGGER_INTERCEPTOR = "LOGGER_INTERCEPTOR"

const val GOOGLE_SERVICE = "GOOGLE_SERVICE"
const val BASE_SERVICE = "BASE_SERVICE"


val retrofitClientModule = applicationContext {
    bean(BASE_URL) { "https://jwk-samu.herokuapp.com/" }
    bean(GOOGLE_URL) { "https://maps.googleapis.com/maps/api/" }
    bean(READ_TIME_OUT) { 60 }
    bean(CONNECT_TIME_OUT) { 60 }

    factory(GOOGLE_SERVICE) {
        Retrofit.Builder()
                .baseUrl(get<String>(GOOGLE_URL))
                .client(/* OkHttpClient */ get())
                .addConverterFactory(/*Converter.Factory*/ get())
                .build()
    }

    factory(BASE_SERVICE) {
        Retrofit.Builder()
                .baseUrl(get<String>(BASE_URL))
                .client(/* OkHttpClient */ get())
                .addConverterFactory(/*Converter.Factory*/ get())
                .build()
    }

    bean {
        OkHttpClient.Builder()
                .connectTimeout(get(CONNECT_TIME_OUT), TimeUnit.SECONDS)
                .readTimeout(get(READ_TIME_OUT), TimeUnit.SECONDS)
                .addInterceptor(get(HEADER_INTERCEPTOR))
                .addInterceptor(get(LOGGER_INTERCEPTOR))
                .build() as OkHttpClient
    }

    bean(HEADER_INTERCEPTOR) {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build())
        }
    }

    bean(LOGGER_INTERCEPTOR) {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    bean { GsonConverterFactory.create(/* Gson */ get()) as Converter.Factory }

    bean {
        Gson()
    }
}