package com.example.haonaph45181_ass.Model.Product

data class ProductRequest(
    val name: String,
    val thumbnail: String,
    val price: Double,
    val description: String
)

data class ProductResponse(
    val msg: String,
    val product: ProductData,
    val success: Boolean
)

data class ProductData(
    val id: String,
    val name: String,
    val thumbnail: String,
    val price: Double,
    val description: String
)
data class UpdateResponse(
    val success: Boolean,
    val message: String
)
data class AddProductResponse(
    val success: Boolean,
    val message: String,
    val id: String // ID tự sinh
)