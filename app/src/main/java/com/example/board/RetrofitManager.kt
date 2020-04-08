package com.example.board

import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


class RetrofitManager {


 var retrofit = Retrofit.Builder()
        .baseUrl("https://2c0f8055.ngrok.io")
        .addConverterFactory(GsonConverterFactory.create())
         .build()

 var request = retrofit.create(MyAPIService::class.java)


 fun add_token_client (token: String){
     
     val httpClient = okhttp3.OkHttpClient().newBuilder()


     httpClient.addInterceptor(object:Interceptor{
         override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

             val origin:Request = chain.request()

             val request = origin.newBuilder()
                 .addHeader("Authorization", "Bearer $token")
                 .method(origin.method(), origin.body())
                 .build()

             return chain.proceed(request)

         }
     })

     val client = httpClient.build()

     retrofit = Retrofit.Builder()
         .baseUrl("https://2c0f8055.ngrok.io")
         .addConverterFactory(GsonConverterFactory.create())
         .client(client)
         .build()

     request = retrofit.create(MyAPIService::class.java)

 }


}