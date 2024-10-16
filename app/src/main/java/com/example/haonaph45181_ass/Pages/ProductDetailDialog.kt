package com.example.haonaph45181_ass.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.haonaph45181_ass.Model.Product.ProductData
import com.example.haonaph45181_ass.ViewModel.AddCartViewModel


@Composable
fun ProductDetailDialog(product: ProductData, onDismiss: () -> Unit, addCartViewModel: AddCartViewModel) {
    // Trạng thái cho số lượng sản phẩm
    var quantity by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Chi tiết sản phẩm",
                fontSize = 20.sp, // Kích thước chữ tiêu đề
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold // In đậm tiêu đề
            )
        },
        text = {
            Column {
                Text(text = "Tên: ${product.name}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Giá: ${product.price}K", fontSize = 18.sp, color = Color(0xFFFFA500), fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) // Màu cam cho giá
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Mô tả: ${product.description}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(5.dp))

                // Hình ảnh sản phẩm
                Image(
                    painter = rememberImagePainter(data = product.thumbnail),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(width = 270.dp, height = 170.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Row để điều khiển số lượng sản phẩm
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(), // Đảm bảo Row sử dụng hết chiều rộng
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center // Căn giữa Row
                ) {
                    // Nút giảm số lượng
                    TextButton(
                        onClick = {
                            if (quantity > 0) quantity--
                        },
                        modifier = Modifier.padding(end = 8.dp).size(48.dp)
                    ) {
                        Text(text = "-", fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }

                    // Hiển thị số lượng
                    Text(
                        text = "$quantity",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 24.sp, // Kích thước chữ số lượng
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color(0xFFFFA500) // Màu cam cho số lượng
                    )

                    // Nút tăng số lượng
                    TextButton(
                        onClick = {
                            quantity++
                        },
                        modifier = Modifier.padding(start = 8.dp).size(48.dp)
                    ) {
                        Text(text = "+", fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // Thêm sản phẩm vào giỏ hàng
                addCartViewModel.addCart(product.id, quantity, onSuccess = {
                    // Xử lý thành công (có thể hiển thị thông báo)
                    onDismiss() // Đóng dialog
                }, onError = { errorMessage ->
                    // Xử lý lỗi (có thể hiển thị thông báo lỗi)
                })
            }) {
                Text("Thêm vào giỏ hàng")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Đóng")
            }
        }
    )
}
