package com.example.myandroidapp.RestApi

import com.example.myandroidapp.Domain.Product
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object Api {
    private const val URL = "http://192.168.100.4:5000/api/v1/"

    interface Service {
        @GET("products")
        suspend fun findAll(): List<Product>


        @GET("products/{id}")
        suspend fun findOne(@Path("id") itemId: String): Product;

        @Headers("Content-Type: application/json")
        @POST("products")
        suspend fun create(@Body product: Product): Product

        @Headers("Content-Type: application/json")
        @PUT("products")
        suspend fun update(@Body product: Product): Product
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()


    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)

}