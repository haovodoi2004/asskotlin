package com.example.haonaph45181_ass.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.haonaph45181_ass.Model.Product.ProductData
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel

@Composable
fun ProductListPage(navController: NavController, viewModel: FetchProductViewModel) {
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchProduct() // Gọi API lấy danh sách sản phẩm
    }

    // Sử dụng LazyColumn để có thể cuộn dọc
    LazyColumn {
        items(products) { product ->
            ProductItem(product = product) {
                viewModel.updateCurrentProduct(product) // Đặt sản phẩm hiện tại để sửa
                viewModel.dialogVisible = true // Hiển thị dialog
            }
        }
        // Hiển thị thông báo nếu không có sản phẩm nào
        if (products.isEmpty()) {
            item {
                Text(text = "Không có sản phẩm nào.", modifier = Modifier.padding(16.dp))
            }
        }
    }

    if (viewModel.dialogVisible) {
        EditProductDialog(viewModel = viewModel)
    }
}

@Composable
fun ProductItem(product: ProductData, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Tạo hiệu ứng bóng
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB0BEC5)) // Màu nền của Card
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Căn giữa các phần tử theo chiều dọc
        ) {
            // Hiển thị ảnh sản phẩm với bo góc
            Image(
                painter = rememberImagePainter(product.thumbnail), // Sử dụng ảnh sản phẩm
                contentDescription = "Ảnh sản phẩm",
                modifier = Modifier
                    .size(80.dp) // Kích thước ảnh
                    .weight(1f)
                    .clip(shape = MaterialTheme.shapes.medium), // Bo góc
                contentScale = ContentScale.Crop // Tỉ lệ khung hình
            )

            // Tên sản phẩm với cỡ chữ lớn hơn
            Text(
                text = product.name,
                modifier = Modifier
                    .weight(2f) // Chiếm nhiều không gian hơn cho tên sản phẩm
                    .padding(start = 16.dp), // Khoảng cách bên trái
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold // Làm cho văn bản in đậm
                )
            )

            // Biểu tượng sửa với kích thước lớn hơn
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp) // Kích thước của IconButton
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, // Sử dụng biểu tượng chỉnh sửa
                    contentDescription = "Sửa",
                    modifier = Modifier.size(64.dp), // Kích thước biểu tượng
                    tint = MaterialTheme.colorScheme.primary // Màu cho biểu tượng
                )
            }
        }
    }
}

