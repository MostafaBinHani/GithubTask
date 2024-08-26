package com.example.voistask.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.voistask.ui.theme.SecondaryColor
import com.example.voistask.viewModel.DetailsViewModel
@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel = viewModel(), login: String) {
    val userDetails by detailsViewModel.userDetails.collectAsState()
    detailsViewModel.fetchUserDetails(login)

    userDetails?.let { user ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.TopCenter)
                    .background(Color.White)
                    .clip(shape = CircleShape)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = user.avatarUrl),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
                    .background(SecondaryColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .background(SecondaryColor),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = user.name ?: user.login,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = user.location ?: "Unknown Location",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = user.bio ?: "No bio available",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
