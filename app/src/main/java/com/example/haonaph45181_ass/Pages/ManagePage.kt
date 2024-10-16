package com.example.haonaph45181_ass.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.haonaph45181_ass.R
import com.example.haonaph45181_ass.toColor

@Composable
fun CardItem(title: String, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onClick?.invoke() },
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors("#808080".toColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Ảnh logo bên trái
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 10.dp)
            )

            // Tiêu đề bên phải của logo
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManagePagePreview() {
    ManagePage(rememberNavController()) // Sử dụng NavController giả cho preview
}

@Composable
fun ManagePage(navController: NavController ,modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
            .padding(top = 0.dp)
            .background("#ffffff".toColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardItem("Quản lý món ăn")  {
            navController.navigate("dish")
        }
    }

}