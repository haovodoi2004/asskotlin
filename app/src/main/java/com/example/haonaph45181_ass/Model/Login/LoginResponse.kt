package com.example.haonaph45181_ass.Model.Login

data class LoginResponse(
    val success: Boolean,
    val msg: String,
    val userId: String,     // ID của người dùng
    val username: String,   // Tên đăng nhập
    val token: String,      // Token xác thực (JWT)
    val message: String     // Thông báo kết quả đăng nhập
)
