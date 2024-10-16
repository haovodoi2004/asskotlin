package com.example.haonaph45181_ass.BottomNavigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.haonaph45181_ass.Pages.AddDishPage
import com.example.haonaph45181_ass.Pages.CartPage
import com.example.haonaph45181_ass.Pages.DeleteProductList
import com.example.haonaph45181_ass.Pages.ManageDishPage
import com.example.haonaph45181_ass.R
import com.example.haonaph45181_ass.Pages.HomePage
import com.example.haonaph45181_ass.Pages.ManagePage
import com.example.haonaph45181_ass.Pages.ProductDetailPage
import com.example.haonaph45181_ass.Pages.ProductListPage
import com.example.haonaph45181_ass.Pages.SupportPage
import com.example.haonaph45181_ass.Pages.UpdateInfoPage
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController() // khoi tao NavController
    val viewModel: FetchProductViewModel = viewModel() // Khai báo viewModel
    var showHeader by remember { mutableStateOf(true) }
    var showBottomBar by remember { mutableStateOf(true) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showHeader = destination.route != "dish"
            showBottomBar = destination.route != "dish" && !destination.route?.startsWith("detail")!!
        }
    }

    Scaffold(
        topBar = {
            val currentDirection = navController.currentBackStackEntry?.destination?.route
            Log.d("MainScreen", "Current Destination (top bar) : $currentDirection")

            //Sử dụng AnimatedVisibility cho Header (TopAppBar)
            AnimatedVisibility(
                visible = showHeader,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),  // Slide từ trên xuống
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ) {
                Header(navController)
            }
        },
        bottomBar = {
            // Sử dụng AnimatedVisibility cho BottomNavigationBar
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),  // Slide từ trên xuống
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ) {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Cấu hình NavHost
        NavHost(
            navController = navController,
            startDestination = "home", // Màn hình bắt đầu
            modifier = Modifier.padding(innerPadding)
        ) {
            // Cấu hình các route
            composable("home") { HomePage(navController) }
            composable("cart") { CartPage() }
            composable("manage") { ManagePage(navController) }
            composable("support") { SupportPage() }
            composable("edit_product_list") { ProductListPage(navController, viewModel) }
            composable("dish") { ManageDishPage(navController) }
            composable("update_info") { UpdateInfoPage() }
            composable("add_dish") { AddDishPage() }
            composable("delete_product_list") { DeleteProductList(navController, viewModel)  }

            composable("detail/{productId}") { backStackEntry ->
                // Lấy id từ đường dẫn điều hướng
                val productId = backStackEntry.arguments?.getString("productId")
                productId?.let {
                    // Truyền id vào ProductDetailPage
                    ProductDetailPage(it)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navItemsList = listOf(
        NavItem("Trang Chủ", Icons.Default.Home),
        NavItem("Giỏ Hàng", Icons.Default.ShoppingCart),
        NavItem("Quản Lí", Icons.Default.Person),
        NavItem("Hỗ Trợ", Icons.Default.Person)
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color.White, // Đặt background màu trắng
        contentColor = Color(0xFFFF5722),  // Màu icon và chữ
    ) {
        navItemsList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (index) {
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("cart")
                        2 -> navController.navigate("manage")
                        3 -> navController.navigate("support")
                    }
                },
                label = { Text(text = navItem.label, color = if (selectedItem == index) Color(0xFFFF5722) else Color.Gray) },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "Icon", tint = if (selectedItem == index) Color(0xFFFF5722) else Color.Gray)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(navController: NavController, onClick: (() -> Unit)? = null) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBackIcon = currentRoute != "home" && currentRoute != "cart" &&
            currentRoute != "manage" && currentRoute != "support" &&
            currentRoute != "dish"

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (showBackIcon) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .padding(end = 8.dp),
                        tint = Color.White // Màu icon back
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigate("update_info") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Tiêu đề
                Text(text = "Cum Tứm Đim", color = Color.White) // Đặt chữ màu trắng
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFFF5722), // Đặt màu nền cho TopAppBar
            titleContentColor = Color.White // Đặt chữ màu trắng

        )
    )
}