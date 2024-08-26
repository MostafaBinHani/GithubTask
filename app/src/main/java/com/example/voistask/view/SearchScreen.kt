import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.voistask.R
import com.example.voistask.model.User
import com.example.voistask.ui.theme.FadedBlack
import com.example.voistask.ui.theme.SecondaryColor
import com.example.voistask.viewModel.SearchViewModel
import coil.compose.rememberImagePainter
import com.example.voistask.ui.theme.PrimaryColor

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel()
) {
    val users by viewModel.users.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isPaginating by viewModel.isPaginating.observeAsState(false)
    val username by viewModel.username.observeAsState("")
    val showInitialText by viewModel.showInitialText.observeAsState(true)

    val listState = rememberLazyListState()

    // Animate the top padding of the TextField based on the visibility of the initial text
    val textFieldTopPadding by animateDpAsState(
        targetValue = if (showInitialText) 32.dp else 0.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (showInitialText) Arrangement.Center else Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
            .padding(10.dp),
    ) {
        // AnimatedVisibility for the initial text
        AnimatedVisibility(
            visible = showInitialText,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Column {
                Image(
                    painter = painterResource(R.drawable.github), // Replace with your image resource ID
                    contentDescription = null, // Provide a description if needed for accessibility
                    modifier = Modifier
                        .padding(horizontal = 16.dp) // Horizontal padding
                        .align(Alignment.CenterHorizontally) // Center horizontally
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Who are you looking for?",
                    color = FadedBlack,
                    style = TextStyle(
                        fontSize = 28.sp, // Adjust font size
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = FadedBlack,
                            blurRadius = 7.0F,
                        )
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp) // Horizontal padding
                        .align(Alignment.CenterHorizontally) // Center horizontally
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = textFieldTopPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = FadedBlack
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
            )
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = { viewModel.onSearchClicked() },
                enabled = username.length >= 4 && !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FadedBlack,
                )
            ) {
                Text("Search")
            }
        }

        // Display a loading indicator if the app is in a loading state
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Display search results with pagination
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(users) { user ->
                UserItem(user)
            }

            // Show loading indicator at the bottom if more data is being loaded
            if (isPaginating) {
                item {
                    LinearProgressIndicator(
                    )
                    //CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }

        // Trigger pagination when scrolling to the bottom
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    if (lastVisibleItemIndex == users.size - 1 && !isPaginating && !isLoading) {
                        viewModel.loadNextPage()
                    }
                }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp)) // Increased spacing between image and text
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = user.login,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = user.id.toString(),
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    }
}


