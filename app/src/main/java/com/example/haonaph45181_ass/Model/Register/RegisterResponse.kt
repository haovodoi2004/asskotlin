package com.example.haonaph45181_ass.Model.Register

data class RegisterResponse(
    val msg: String,
    val success: Boolean,
    val userId: String,     // ID của người dùng sau khi đăng ký thành công
    val message: String     // Thông báo kết quả đăng ký
)
