package com.example.haonaph45181_ass

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haonaph45181_ass.Model.AuthViewModel
import com.example.haonaph45181_ass.ui.theme.Haonaph45181_assTheme

class LoginScreen : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Haonaph45181_assTheme {
                Login()
            }
        }
    }

    val String.toColor: Color
        get() = Color(android.graphics.Color.parseColor(this))

    @Composable
    fun Login() {

        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val context = LocalContext.current

        // Lắng nghe các thay đổi từ LiveData
        LaunchedEffect(authViewModel.loginResponse) {
            authViewModel.loginResponse.observeForever {
                it?.let {
                    // Xử lý khi đăng nhập thành công
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    // Chuyển đến màn hình chính
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }

        // Lắng nghe lỗi
        LaunchedEffect(authViewModel.errorMessage) {
            authViewModel.errorMessage.observeForever { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = "#ffffff".toColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "My Image",
                modifier = Modifier
                    .size(290.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Đăng nhập",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, bottom = 4.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            TextField2(
                title = "Username",
                placeholder = "Enter your username",
                trailingIcon = {
                    IconButton(onClick = { /* Xử lý khi nhấn */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                value = username.value,
                onValueChange = { username.value = it }
            )
            TextField2(
                title = "Password",
                placeholder = "Enter your password",
                trailingIcon = {
                    IconButton(onClick = { /* Xử lý khi nhấn */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_24),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                value = password.value,
                onValueChange = { password.value = it }
            )

            Text(
                text = "Forgot password?",
                color = "#FE724C".toColor, // Màu cam
                modifier = Modifier.padding(bottom = 16.dp, top = 4.dp)
            )

            val context = LocalContext.current

            Button(
                onClick = {
                    authViewModel.login(username.value, password.value)
                },
                modifier = Modifier
                    .padding(13.dp)
                    .height(50.dp)
                    .width(170.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = "#FE724C".toColor,
                    contentColor = "#FFFFFF".toColor
                )
            ) {
                Text(text = "LOGIN")
            }

            Row(
                modifier = Modifier.padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                TextButton(
                    onClick = {
                        val intent = Intent(context, SignUpScreen::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        text = "Sign Up",
                        color = "#FE724C".toColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TextField2(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.Black // Đặt màu chữ title là màu trắng
        )

        OutlinedTextField(
            value = value,
            modifier = Modifier
                .width(400.dp),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Black // Đặt màu chữ placeholder là màu trắng
                )
            },
            textStyle = TextStyle(
                color = Color.Black, // Màu chữ khi nhập là màu trắng
                fontSize = 16.sp // Kích thước chữ
            ),
            trailingIcon = {
                trailingIcon?.invoke()
            },
        )
    }
}

