package com.example.haonaph45181_ass.Pages

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel

@Composable
fun EditProductDialog(viewModel: FetchProductViewModel) {
    val product = viewModel.currentProduct
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Dialog(onDismissRequest = { viewModel.DialogVisible(false) }) {
        Surface(
            modifier = Modifier
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp // Tăng chiều sâu
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa các thành phần
            ) {
                // Tiêu đề
                Text(
                    text = "Chỉnh sửa sản phẩm",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Tên sản phẩm
                TextField(
                    value = product?.name ?: "",
                    onValueChange = { viewModel.updateCurrentProductName(it) },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Giá sản phẩm
                TextField(
                    value = product?.price?.toString() ?: "",
                    onValueChange = { viewModel.updateCurrentProductPrice(it.toDoubleOrNull() ?: 0.0) },
                    label = { Text("Giá sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Mô tả sản phẩm
                TextField(
                    value = product?.description ?: "",
                    onValueChange = { viewModel.updateCurrentProductDescription(it) },
                    label = { Text("Mô tả sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Hiển thị ảnh sản phẩm hiện tại (nếu có)
                product?.thumbnail?.let {
                    Image(
                        painter = rememberImagePainter(it),
                        contentDescription = "Ảnh sản phẩm",
                        modifier = Modifier
                            .width(270.dp) // Chiều rộng cố định cho ảnh
                            .height(170.dp) // Chiều cao cố định cho ảnh
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Button để chọn ảnh mới
                Button(
                    onClick = {
                        // Gọi ImagePicker hoặc bất kỳ logic nào để chọn ảnh từ thư viện
                        viewModel.pickImage(imageUri) // Implement logic to pick image
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Chọn ảnh mới")
                }

                // Nút lưu với chữ "Lưu"
                Button(
                    onClick = {
                        if (product != null) {
                            val newName = product.name
                            val newPrice = product.price
                            val newDescription = product.description

                            // Kiểm tra tên sản phẩm
                            if (newName.isBlank() || newDescription.isBlank() || newPrice <= 0) {
                                // Hiển thị thông báo lỗi
                                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.updateProductOnServer(imageUri) // Gửi cập nhật lên server
                                viewModel.DialogVisible(false)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.End),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Lưu")
                }
            }
        }
    }
}
