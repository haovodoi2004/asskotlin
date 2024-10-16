package com.example.haonaph45181_ass.ViewModel

import android.annotation.SuppressLint
import com.example.haonaph45181_ass.Model.Product.AddProductResponse
import com.example.haonaph45181_ass.Model.Product.ProductRequest
import com.example.haonaph45181_ass.Model.Product.ProductResponse
import com.example.haonaph45181_ass.Retrofit.RetrofitClient
import com.example.haonaph45181_ass.Service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddDishViewModel {
    private val apiService: ApiService = RetrofitClient.api

    fun addProduct(
        name: String,
        price: Double,
        description: String,
        thumbnail: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val call = apiService.addProduct(name, price, description, thumbnail)
        call.enqueue(object : Callback<AddProductResponse> {
            override fun onResponse(
                call: Call<AddProductResponse>,
                response: Response<AddProductResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            onSuccess()
                        } else {
                            onError(it.message)
                        }
                    }
                } else {
                    onError("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                onError("Failure: ${t.localizedMessage ?: "Unknown error"}")
            }
        })
    }
}