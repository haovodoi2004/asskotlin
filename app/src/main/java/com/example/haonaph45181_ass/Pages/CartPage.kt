package com.example.haonaph45181_ass.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.haonaph45181_ass.toColor
import com.example.haonaph45181_ass.ViewModel.CartViewModel

@Composable
fun CartPage(modifier: Modifier = Modifier) {

    val viewModelCart: CartViewModel = remember { CartViewModel() }
    LaunchedEffect(Unit) {
        viewModelCart.fetchCart()
    }

    // Lấy dữ liệu từ LiveData
    val carts by viewModelCart.carts.observeAsState()



    Column(
        modifier = modifier
            .fillMaxSize()
            .background("#F6F6F6".toColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cart Page",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green
        )


    }

}

