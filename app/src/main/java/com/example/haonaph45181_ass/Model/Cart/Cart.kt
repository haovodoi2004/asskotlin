package com.example.haonaph45181_ass.Model.Cart

import com.example.haonaph45181_ass.Model.Product.ProductData

data class CartRequest(
    val productId: String,
    val quantity: Int
)

data class CartResponse(
    val msg: String,
    val cart: List<CartData>? = null // Danh sách sản phẩm trong giỏ hàng (tuỳ chọn)
)


data class CartData(
    val product: ProductData, // Dữ liệu sản phẩm
    val quantity: Int,
    val _id: String // ID của sản phẩm trong giỏ hàng
)


