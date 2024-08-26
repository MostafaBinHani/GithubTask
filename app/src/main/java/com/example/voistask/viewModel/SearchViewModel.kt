package com.example.voistask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voistask.model.User
import com.example.voistask.model.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isPaginating = MutableLiveData(false)
    val isPaginating: LiveData<Boolean> = _isPaginating

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _showInitialText = MutableLiveData(true)
    val showInitialText: LiveData<Boolean> = _showInitialText

    private var isLastPage = false
    private var currentPage = 1

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onSearchClicked() {
        _showInitialText.value = false
        _isLoading.value = true

        _users.value = emptyList()
        isLastPage = false
        currentPage = 1

        viewModelScope.launch {
            try {
                val fetchedUsers = userRepository.fetchUsers(_username.value ?: "", currentPage)
                _users.value = fetchedUsers
                isLastPage = fetchedUsers.isEmpty()
            } catch (_: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isLoading.value == true || _isPaginating.value == true || isLastPage) return

        _isPaginating.value = true
        currentPage++
        viewModelScope.launch {
            try {
                val fetchedUsers = userRepository.fetchUsers(_username.value ?: "", currentPage)

                if (fetchedUsers.isNotEmpty()) {
                    _users.value = _users.value?.plus(fetchedUsers)
                } else {
                    isLastPage = true
                }
            } catch (_: Exception) {
            } finally {
                _isPaginating.value = false
            }
        }
    }
}
