package com.example.haonaph45181_ass.Retrofit

import com.example.haonaph45181_ass.Service.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var token: String? = null  // Add this line to manage the token


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            // Thêm token vào request nếu token không null
            RetrofitClient.token?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)  // Logging Interceptor để log các request và response
        .connectTimeout(30, TimeUnit.SECONDS) // Thời gian chờ kết nối
        .readTimeout(30, TimeUnit.SECONDS)    // Thời gian chờ đọc dữ liệu
        .writeTimeout(30, TimeUnit.SECONDS)   // Thời gian chờ ghi dữ liệu
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://comtam.phuocsangbn.workers.dev/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }


}







//    var token: String? = null
//
//    // Cập nhật đường dẫn cơ sở của API
//    private const val BASE_URL = "https://comtam.phuocsangbn.workers.dev/" // Đường dẫn cơ sở mới
//
//    private fun createOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val request: Request.Builder = chain.request().newBuilder()
//
//                // Lấy token từ SharedPreferences
//                val token = RetrofitClient.token
//
//                // Nếu token không null, thêm vào header
//                if (token != null) {
//                    request.addHeader("Authorization", "Bearer $token")
//                }
//
//                chain.proceed(request.build())
//            }
//            .connectTimeout(30, TimeUnit.SECONDS) // Thời gian chờ kết nối
//            .readTimeout(30, TimeUnit.SECONDS)    // Thời gian chờ đọc dữ liệu
//            .writeTimeout(30, TimeUnit.SECONDS)   // Thời gian chờ ghi dữ liệu
//            .build()    }
//
//    val instance: ApiService by lazy {
//        val retrofit = Retrofit.Builder()
//            .client(createOkHttpClient())
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        retrofit.create(ApiService::class.java)
//    }


