package com.example.voistask

import SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voistask.model.SearchRepo
import com.example.voistask.model.UserRepository
import com.example.voistask.view.DetailsScreen
import com.example.voistask.ui.theme.VoisTaskTheme
import com.example.voistask.viewModel.SearchViewModel
import com.example.voistask.viewModel.DetailsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            VoisTaskTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val searchRepo = SearchRepo()
                    val userRepository = UserRepository(searchRepo)
                    NavHost(navController = navController, startDestination = "search") {
                        composable("search") {
                            SearchScreen(
                                viewModel = remember { SearchViewModel(userRepository) },
                                onUserClick = { user ->
                                    navController.navigate("details/${user.login}")
                                }
                            )
                        }
                        composable("details/{login}") { backStackEntry ->
                            val login = backStackEntry.arguments?.getString("login") ?: return@composable
                            DetailsScreen(
                                detailsViewModel = remember { DetailsViewModel(userRepository) },
                                login = login,
                                navController = navController // Pass NavController to DetailsScreen
                            )
                        }
                    }
                }
            }
        }
    }
}
