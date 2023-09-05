package com.example.bodyboost

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitManager {
    private var retrofitAPI: RetrofitAPI? = null

    fun getInstance(): RetrofitAPI {
        if (retrofitAPI == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", Credentials.basic("id", "password"))
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

            retrofitAPI = Retrofit.Builder()
                .baseUrl("https://b27c-49-216-93-143.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitAPI::class.java)
        }
        return retrofitAPI!!
    }

}
