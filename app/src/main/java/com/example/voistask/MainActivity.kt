package com.example.voistask

import SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voistask.model.SearchRepo
import com.example.voistask.model.UserRepository
import com.example.voistask.ui.theme.VoisTaskTheme
import com.example.voistask.viewModel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            VoisTaskTheme {
                Surface(color = MaterialTheme.colorScheme.background){
                    val navController = rememberNavController()
                    val searchRepo = SearchRepo()
                    val userRepository = UserRepository(searchRepo)
                    NavHost(navController = navController, startDestination = "search" ) {
                        composable("search"){
                            SearchScreen(viewModel = remember { SearchViewModel(userRepository) })
                        }

                    }
                }
            }
        }
    }
}