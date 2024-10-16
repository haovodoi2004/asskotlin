package com.example.haonaph45181_ass.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.haonaph45181_ass.R
import com.example.haonaph45181_ass.toColor
import com.example.haonaph45181_ass.ViewModel.AddCartViewModel
import com.example.haonaph45181_ass.ViewModel.FetchProductViewModel

@Composable
fun ProductDetailPage(productId: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val productViewModel: FetchProductViewModel = viewModel()
    val addCartViewModel: AddCartViewModel = viewModel()

    // Gọi fetchProductById để tải dữ liệu
    LaunchedEffect(productId) {
        productViewModel.fetchProductById(productId)
    }
    // Theo dõi LiveData
    val productDetail by productViewModel.productDetail.observeAsState()

    Log.d("detail", productDetail.toString())
    var isExpanded by remember { mutableStateOf(false) } // Trạng thái để kiểm soát mở rộng mô tả
    var quantity by remember { mutableStateOf(1) } // Biến để lưu số lượng sản phẩm



    Column(
        modifier = modifier
            .fillMaxSize()
            .background("#F6F6F6".toColor),
    ){
        // Kiểm tra productDetail và hiển thị thông tin
        productDetail?.let { foods ->
            AsyncImage(
                model = foods.thumbnail, // Sử dụng image_url từ product
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Món: ${foods.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)

                )
            }
            // Row chứa giá và nút tăng/giảm số lượng sản phẩm
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Giá: ${foods.price}k",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                // Bộ điều khiển số lượng sản phẩm
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(10.dp))
                        .padding(4.dp)
                ) {
                    // Nút giảm số lượng
                    Icon(
                        painter = painterResource(R.drawable.ic_minus), // Thay icon theo tài nguyên của bạn
                        contentDescription = "Decrease quantity",
                        modifier = Modifier
                            .clickable {
                                // Xử lý khi nhấn nút giảm số lượng
                                if (quantity > 1) quantity--
                            }
                    )
                    // Hiển thị số lượng sản phẩm
                    Text(
                        text = "$quantity", // Số lượng sản phẩm
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(8.dp)
                    )
                    // Nút tăng số lượng
                    Icon(
                        painter = painterResource(R.drawable.ic_plus), // Thay icon theo tài nguyên của bạn
                        contentDescription = "Increase quantity",
                        modifier = Modifier
                            .clickable {
                                // Xử lý khi nhấn nút tăng số lượng
                                quantity++
                            }
                    )
                }
            }

            // Tạo đường border dưới hai thẻ Text
            Divider(
                color = "#C2A8A8".toColor,
                thickness = 1.dp,
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                ) // Thêm khoảng cách trên đường border
            )

            Text(
                text = "Mô Tả",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            // Kiểm tra chiều dài mô tả
            val description = foods.description
            val showReadMoreButton = description.length > 80 // Kiểm tra độ dài mô tả

            Text(
                text = if (isExpanded) description else "${description.take(80)}${if (showReadMoreButton) "..." else ""}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )

            // Hiển thị nút "Đọc thêm" nếu mô tả dài hơn 50 ký tự
            if (showReadMoreButton) {
                Text(
                    text = if (isExpanded) "Đọc ít lại" else "Đọc thêm",
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp)
                        .clickable {
                            isExpanded = !isExpanded // Chuyển trạng thái mở rộng
                        }
                )
            }

            // Row chứa nút Add to Cart và bộ điều khiển số lượng sản phẩm
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 90.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tính tổng tiền
                val totalPrice = quantity * foods.price // Nhân số lượng với giá sản phẩm

                Text(
                    text = "Tổng Tiền: ${totalPrice}k",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Nút Add To Cart
                Button(
                    onClick = {
                        // Xử lý sự kiện thêm vào giỏ hàng tại đây
                        addCartViewModel.addCart(productId, quantity,
                            onSuccess = {
                                Log.d(
                                    "Cart",
                                    "Added to cart: ${foods.name} with quantity $quantity"
                                )
                                Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()

                            },
                            onError = { errorMessage ->
                                Log.e("Cart Error", errorMessage)
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = "#FE724C".toColor),
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Add To Cart", color = Color.White)
                }
            }


        } ?: run {
            Text(
                text = "Loading...", // Hoặc một thông báo cho biết rằng sản phẩm chưa được tải
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

    }

}