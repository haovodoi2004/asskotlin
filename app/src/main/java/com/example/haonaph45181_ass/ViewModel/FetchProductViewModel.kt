package com.example.haonaph45181_ass.ViewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haonaph45181_ass.Model.Product.ProductData
import com.example.haonaph45181_ass.Model.Product.UpdateResponse
import com.example.haonaph45181_ass.Retrofit.RetrofitClient
import com.example.haonaph45181_ass.Service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class FetchProductViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.api

    // StateFlow cho danh sách sản phẩm
    private val _products = MutableStateFlow<List<ProductData>>(emptyList())
    val products: StateFlow<List<ProductData>> = _products

    private val client = OkHttpClient()

    // Biến để lưu trữ sản phẩm hiện tại đang được chỉnh sửa
    var currentProduct by mutableStateOf<ProductData?>(null)
        private set

    var dialogVisible by mutableStateOf(false)

    // LiveData cho chi tiết sản phẩm
    private val _productDetail = MutableLiveData<ProductData?>()
    val productDetail: LiveData<ProductData?> = _productDetail


    // Lấy danh sách sản phẩm
    fun fetchProduct() {
        viewModelScope.launch {
            apiService.getProducts().enqueue(object : Callback<List<ProductData>?> {
                override fun onResponse(
                    call: Call<List<ProductData>?>,
                    response: Response<List<ProductData>?>
                ) {
                    if (response.isSuccessful) {
                        _products.value = response.body() ?: emptyList()
                        Log.d("FetchProductViewModel", "Fetch products success")
                    } else {
                        Log.e("FetchProductViewModel", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<ProductData>?>, t: Throwable) {
                    Log.e("FetchProductViewModel", "Fetch products failed: ${t.message}")
                }
            })
        }
    }

    fun fetchProductById(productId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getProductById(productId).awaitResponse()
                val rawJson = response.errorBody()?.string() // Nếu có lỗi
                Log.e("FetchProductViewModel", "Raw JSON Response: $rawJson") // Kiểm tra nội dung JSON

                if (response.isSuccessful) {
                    _productDetail.value = response.body()
                    Log.d("FetchProductViewModel", "Fetch product by id success")
                } else {
                    _productDetail.value = null
                    Log.e("FetchProductViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _productDetail.value = null
                Log.e("FetchProductViewModel", "Error fetching product by id: ${e.message}")
            }
        }
    }

    // Tìm kiếm sản phẩm theo query
    fun searchProduct(query: String) {
        apiService.searchProducts(query).enqueue(object : Callback<List<ProductData>?> {
            override fun onResponse(
                call: Call<List<ProductData>?>,
                response: Response<List<ProductData>?>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("ProductViewModel", "Search result: $result")
                    _products.value = result ?: emptyList()
                } else {
                    Log.e("ProductViewModel", "Search error: ${response.errorBody()?.string()}")
                    _products.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<ProductData>?>, t: Throwable) {
                Log.e("ProductViewModel", "Search failed: ${t.message}")
                _products.value = emptyList()
            }
        })
    }


    // Hàm xóa sản phẩm
    fun deleteProduct(_id: String, onResult: (Boolean, String) -> Unit) {
        val call = RetrofitClient.api.deleteProduct(_id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true, "Sản phẩm đã bị xóa thành công!")
                } else {
                    onResult(false, "Không thể xóa sản phẩm: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onResult(false, "Lỗi: ${t.message}")
            }
        })
    }


    fun pickImage(uri: Uri?) {
        if (uri != null) {
            // Cập nhật ảnh mới vào sản phẩm hiện tại
            currentProduct = currentProduct?.copy(thumbnail = uri.toString())
        }
    }

    // Cập nhật sản phẩm trên server
    fun updateProductOnServer(imageUri: Uri?) {
        viewModelScope.launch {
            currentProduct?.let { product ->
                // Upload ảnh nếu có Uri, nếu không giữ nguyên ảnh cũ
                val imageUrl = imageUri?.let { uploadImageToServer(it) } ?: product.thumbnail
                // Gọi API để cập nhật sản phẩm
                apiService.updateProduct(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    thumbnail = imageUrl
                ).enqueue(object : Callback<UpdateResponse> {
                    override fun onResponse(
                        call: Call<UpdateResponse>,
                        response: Response<UpdateResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("FetchProductViewModel", "Product updated successfully")
                        } else {
                            Log.e(
                                "FetchProductViewModel",
                                "Update failed: ${response.errorBody()?.string()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                        Log.e("FetchProductViewModel", "Update failed: ${t.message}")
                    }
                })
            }
        }
    }

    // Giả lập upload ảnh lên server, trả về URL
    private fun uploadImageToServer(imageUri: Uri): String {
        // Logic upload ảnh lên server
        return "https://example.com/uploaded_image.jpg" // Trả về URL của ảnh upload
    }

    // Cập nhật các thuộc tính của sản phẩm hiện tại

    fun updateCurrentProduct(product: ProductData) {
        currentProduct = product
    }

    fun DialogVisible(visible: Boolean) {
        dialogVisible = visible
    }

    fun updateCurrentProductName(name: String) {
        currentProduct = currentProduct?.copy(name = name)
    }

    fun updateCurrentProductPrice(price: Double) {
        currentProduct = currentProduct?.copy(price = price)
    }

    fun updateCurrentProductDescription(description: String) {
        currentProduct = currentProduct?.copy(description = description)
    }
}