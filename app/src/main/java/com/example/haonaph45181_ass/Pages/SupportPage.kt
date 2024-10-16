package com.example.haonaph45181_ass.Pages

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haonaph45181_ass.LoginScreen
import com.example.haonaph45181_ass.Retrofit.RetrofitClient
import com.example.haonaph45181_ass.toColor

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SupportPage(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    Column (
        modifier = modifier.fillMaxSize()
            .background("#F6F6F6".toColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Support Page",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green
        )

        Button(
            onClick = {
                logoutUser(context) // Gọi hàm logout khi nhấn nút
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Logout")
        }

    }

}

fun logoutUser(context: Context) {
    // Lấy SharedPreferences
    val sharedPref = context.getSharedPreferences("MY_APP_PREFS", MODE_PRIVATE)
    val editor = sharedPref.edit()

    // Xóa token khỏi SharedPreferences
    editor.remove("auth_token")
    RetrofitClient.token = null
    editor.apply()

    // Chuyển hướng về LoginScreen
    val intent = Intent(context, LoginScreen::class.java)
    intent.flags =
        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Xóa stack activity trước đó
    context.startActivity(intent)

    // Kết thúc Activity hiện tại (nếu có)
    if (context is Activity) {
        context.finish()
    }
}
