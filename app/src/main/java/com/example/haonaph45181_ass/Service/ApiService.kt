package com.example.haonaph45181_ass.Service

import com.example.haonaph45181_ass.Model.Cart.CartData
import com.example.haonaph45181_ass.Model.Cart.CartRequest
import com.example.haonaph45181_ass.Model.Cart.CartResponse
import com.example.haonaph45181_ass.Model.Product.ProductData
import com.example.haonaph45181_ass.Model.Login.LoginResponse
import com.example.haonaph45181_ass.Model.Product.AddProductResponse
import com.example.haonaph45181_ass.Model.Product.ProductRequest
import com.example.haonaph45181_ass.Model.Product.ProductResponse
import com.example.haonaph45181_ass.Model.Product.UpdateResponse
import com.example.haonaph45181_ass.Model.Register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("/signup")
    fun signUp(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/foods")
    fun getProducts(): Call<List<ProductData>>

    @FormUrlEncoded
    @POST("/searchFood")
    fun searchProducts(
        @Field("text") query: String // Gửi tham số tìm kiếm
    ): Call<List<ProductData>>

    @FormUrlEncoded
    @POST("/createFood")
    fun addProduct(
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("thumbnail") thumbnail: String
    ): Call<AddProductResponse>

    @FormUrlEncoded
    @POST("/updateFood")
    fun updateProduct(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("thumbnail") thumbnail: String
    ): Call<UpdateResponse>

    @GET("deleteFood")
    fun deleteProduct(@Query("id") id: String): Call<Void>

    // Lấy sản phẩm theo ID
    @GET("/foods/{id}")
    fun getProductById(@Path("id") productId: String): Call<ProductData>


    @POST("products/cart")
    fun addToCart(@Body cartRequest: CartRequest) : Call<CartResponse>

    @GET("products/get-cart")
    fun getCart() : Call<List<CartData>>

}