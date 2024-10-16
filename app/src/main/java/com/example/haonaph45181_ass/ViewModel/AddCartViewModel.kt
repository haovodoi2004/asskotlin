package com.example.haonaph45181_ass.ViewModel

import androidx.lifecycle.ViewModel
import com.example.haonaph45181_ass.Model.Cart.CartRequest
import com.example.haonaph45181_ass.Model.Cart.CartResponse
import com.example.haonaph45181_ass.Retrofit.RetrofitClient
import com.example.haonaph45181_ass.Service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCartViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.api

    fun addCart(
        productId: String,
        quatity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val cartRequest = CartRequest(
            productId = productId,
            quantity = quatity
        )

        apiService.addToCart(cartRequest).enqueue(object : Callback<CartResponse?> {
            override fun onResponse(call: Call<CartResponse?>, response: Response<CartResponse?>) {
                if (response.isSuccessful) {
                    // Thành công
                    onSuccess()
                } else {
                    // Xử lý lỗi từ server
                    onError("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CartResponse?>, t: Throwable) {
                onError("Failure: ${t.message}")

            }
        })
    }
}