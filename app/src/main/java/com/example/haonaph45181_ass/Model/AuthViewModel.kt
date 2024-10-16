package com.example.haonaph45181_ass.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haonaph45181_ass.Model.Login.LoginResponse
import com.example.haonaph45181_ass.Model.Register.RegisterResponse
import com.example.haonaph45181_ass.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel(){

        // LiveData cho phản hồi đăng nhập và đăng ký
        private val _loginResponse = MutableLiveData<LoginResponse?>()
        val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

        private val _registerResponse = MutableLiveData<Boolean>()
        val registerResponse: LiveData<Boolean> get() = _registerResponse

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String> get() = _errorMessage

        // Xử lý đăng nhập
        fun login(username: String, password: String) {
            if (username.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Tên người dùng và mật khẩu không được bỏ trống"
                return
            }

            // Sử dụng RetrofitClient.api để gọi API
            RetrofitClient.api.login(username, password).enqueue(object :
                Callback<LoginResponse?> {
                override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                    if (response.isSuccessful) {
                        _loginResponse.value = response.body()
                    } else {
                        _errorMessage.value = "Đăng nhập thất bại: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    _errorMessage.value = "Lỗi khi đăng nhập: ${t.message}"
                }
            })
        }

        // Xử lý đăng ký
        fun register(fullName: String, username: String, password: String) {
            if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Các trường không được bỏ trống"
                return
            }

            // Sử dụng RetrofitClient.api để gọi API
            RetrofitClient.api.signUp(username, password, fullName).enqueue(object : Callback<RegisterResponse?> {
                override fun onResponse(call: Call<RegisterResponse?>, response: Response<RegisterResponse?>) {
//                private val _registerSuccess = MutableLiveData<Boolean>()
//                val registerSuccess: LiveData<Boolean> get() = _registerSuccess
                    if (response.isSuccessful) {
                        _registerResponse.value = true
                        _errorMessage.value = "Đăng ký thành công: ${response.code()}"
                    } else {
                        _errorMessage.value = "Đăng ký thất bại: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                    _errorMessage.value = "Lỗi khi đăng ký: ${t.message}"
                }
            })
        }

        // Làm trống dữ liệu
        fun clearResponses() {
            _loginResponse.value = null
            _registerResponse.value = false
            _errorMessage.value = ""
        }
}