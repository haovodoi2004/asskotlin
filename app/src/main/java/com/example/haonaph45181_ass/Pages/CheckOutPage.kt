package com.example.haonaph45181_ass.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haonaph45181_ass.toColor

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckOut(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) } // State để điều khiển modal

    Column(
        modifier = modifier
            .fillMaxSize()
            .background("#F6F6F6".toColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomerInfo(
            name = "Nguyễn Văn A",
            address = "Tây Mỗ - Nam Từ Liêm - Hà Nội",
            phone = "0123456789"
        )

        PaymentInfo(
            cardNumber = "**** **** **** 1234",
            expirationDate = "12/25",
            cardHolderName = "Nguyễn Văn A"
        )

        TotalAmountCard("200.000") {
            showDialog = true // Hiện dialog khi nhấn nút thanh toán
        }
    }

    // Modal xác nhận thanh toán
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false // Đóng dialog khi nhấn ra ngoài
            },
            title = {
                Text(text = "Xác Nhận Thanh Toán")
            },
            text = {
                Text(text = "Bạn có chắc chắn muốn thanh toán không?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Xử lý xác nhận thanh toán ở đây
                        showDialog = false // Đóng dialog
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text("Có")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false // Đóng dialog khi nhấn nút "Không"
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Không")
                }
            }
        )
    }
}

@Composable
fun TotalAmountCard(totalAmount: String, onPaymentClick: () -> Unit) {
    // Card để hiển thị tổng tiền và nút thanh toán
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors("#FF9800".toColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tổng Tiền: $totalAmount",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Thêm khoảng cách giữa tổng tiền và nút thanh toán
            Button(
                onClick = onPaymentClick, // Gọi hàm để hiện dialog
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Thanh Toán",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PaymentInfo(
    cardNumber: String,
    expirationDate: String,
    cardHolderName: String
) {
    // Card để hiển thị thông tin thanh toán
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors("#4CAF50".toColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Số Thẻ: $cardNumber",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Ngày Hết Hạn: $expirationDate",
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "Tên Chủ Thẻ: $cardHolderName",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CustomerInfo(
    name: String,
    address: String,
    phone: String
) {
    // Card để hiển thị thông tin khách hàng
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors("#FE724C".toColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Khách Hàng: $name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Địa chỉ: $address",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Số Điện Thoại: $phone",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
