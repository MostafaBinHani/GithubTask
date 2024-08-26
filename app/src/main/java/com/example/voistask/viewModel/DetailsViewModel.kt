package com.example.voistask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voistask.model.User
import com.example.voistask.model.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    fun fetchUserDetails(login: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.fetchUserDetails(login)
                _userDetails.value = user
            } catch (_: Exception) {

            }
        }
    }
}