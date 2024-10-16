package com.example.haonaph45181_ass.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.haonaph45181_ass.Model.Product.ProductData
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel

@Composable
fun DeleteProductList(navController: NavController, viewModel: FetchProductViewModel) {
    val products by viewModel.products.collectAsState(initial = emptyList())
    var productToDelete by remember { mutableStateOf<ProductData?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.fetchProduct()
        products.forEach {
            Log.d("ProductData", "Product ID: ${it.id}, Name: ${it.name}")
        }
    }

    if (showDialog && productToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Xác nhận xóa") },
            text = { Text(text = "Bạn có chắc chắn muốn xóa sản phẩm '${productToDelete!!.name}' với ID '${productToDelete!!.id}' không?") },
            confirmButton = {
                TextButton(onClick = {
                    productToDelete?.id?.let { id ->
                        Log.d("DeleteProduct", "Deleting product with ID: $id")
                        viewModel.deleteProduct(id) { success, message ->
                            if (success) {
                                Toast.makeText(navController.context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show()
                                viewModel.fetchProduct()
                            } else {
                                Log.e("DeleteProduct", message)
                            }
                            showDialog = false // Đóng hộp thoại
                        }
                    } ?: run {
                        // Nếu _id null, log lỗi
                        Log.e("DeleteProduct", "Product ID is null, cannot delete")
                    }
                }) {
                    Text("Có")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Không")
                }
            }
        )
    }

    LazyColumn {
        items(products) { product ->
            ProductItemDelete(product = product, onDeleteClick = {
                productToDelete = product
                showDialog = true
            })
        }
        if (products.isEmpty()) {
            item {
                Text(text = "Không có sản phẩm nào.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ProductItemDelete(product: ProductData, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hiển thị ảnh sản phẩm
            Image(
                painter = rememberImagePainter(product.thumbnail),
                contentDescription = "Ảnh sản phẩm",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            // Tên sản phẩm
            Text(
                text = product.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )

            // Nút xóa
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete, // Biểu tượng xóa
                    contentDescription = "Xóa",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
