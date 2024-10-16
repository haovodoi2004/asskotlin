package com.example.haonaph45181_ass.Pages

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.haonaph45181_ass.Model.Product.ProductData
import com.example.haonaph45181_ass.R
import com.example.haonaph45181_ass.ViewModel.AddCartViewModel
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel
import com.example.haonaph45181_ass.toColor


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun fake1() {
    HomePage(rememberNavController())
}


@Composable
fun HomePage(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModelProduct: FetchProductViewModel = remember { FetchProductViewModel() }

    // Sử dụng collectAsState() để lấy giá trị từ StateFlow
    val products by viewModelProduct.products.collectAsState(initial = emptyList())

    // State lưu trữ giá trị tìm kiếm
    var searchProduct by remember { mutableStateOf(TextFieldValue("")) }

    // State lưu trữ sản phẩm hiện tại được chọn và trạng thái hiển thị dialog
    var showDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<ProductData?>(null) }

    val addCartViewModel: AddCartViewModel = viewModel()

    // Khởi tạo launcher
    LaunchedEffect(Unit) {
        viewModelProduct.fetchProduct()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White), // Đặt màu nền thành trắng
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(R.drawable.banner),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp)) // Bo góc 16.dp cho ảnh
        )

        // Ô tìm kiếm
        OutlinedTextField(
            value = searchProduct,
            onValueChange = { newText ->
                searchProduct = newText
                if (newText.text.isNotEmpty()) {
                    viewModelProduct.searchProduct(newText.text)
                } else {
                    viewModelProduct.fetchProduct()
                }
            },
            label = { Text("Tìm kiếm món ăn", color = Color.Black) }, // Đặt màu chữ label thành đen
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Black) // Đặt màu icon tìm kiếm
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            textStyle = TextStyle(color = Color.Black) // Đặt màu chữ tìm kiếm thành đen
        )

        // Hiển thị danh sách sản phẩm
        if (products.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products) { product: ProductData ->
                    ProductItemCT(
                        product = product,
                        onClick = {
                            selectedProduct = product // Cập nhật sản phẩm được chọn
                            showDialog = true          // Hiển thị dialog
                        }
                    )
                }
            }
        } else {
            Text(text = "Không có sản phẩm nào", modifier = Modifier.padding(16.dp), color = Color.Black) // Đặt màu chữ thành đen
        }
    }
    // Hiển thị dialog nếu cần
    if (showDialog && selectedProduct != null) {
        ProductDetailDialog(
            product = selectedProduct!!,
            onDismiss = { showDialog = false }, // Tắt dialog khi bấm dismiss
            addCartViewModel = addCartViewModel
        )
    }
}

@Composable
fun ProductItemCT(product: ProductData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }  // Kích hoạt sự kiện onClick
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f) // Semi-transparent white
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Add shadow effect
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageUri = Uri.parse(product.thumbnail)
            Log.d("IMG", imageUri.toString())

            DisplayImageFromUri(product.thumbnail)
            Column(
                modifier = Modifier.padding(top = 8.dp, start = 10.dp)
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black) // Đặt màu chữ thành đen
                Text(text = "${product.price}K", fontSize = 16.sp, color = "#FE724C".toColor) // Giữ màu giá
            }
        }
    }
}

@Composable
fun DisplayImageFromUri(imageUri: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUri,
            builder = {
                error(R.drawable.error_placeholder) // Hiển thị ảnh lỗi nếu không tải được
                listener(
                    onError = { request, throwable ->
                        Log.e(
                            "CoilError",
                            "Error loading image: ${throwable.throwable ?: "Unknown error"}"
                        )
                    },
                    onSuccess = { _, _ ->
                        Log.d("CoilSuccess", "Image loaded successfully")
                    }
                )
            }
        ),
        contentDescription = "Selected Image",
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .size(60.dp), // Chỉnh sửa kích thước theo nhu cầu
        contentScale = ContentScale.Crop
    )
}
