package sk.parohy.codecon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.failure
import sk.parohy.codecon.api.success

private const val USERNAME = "username"
private const val PASSWORD = "password"

class LoginViewModel: ViewModel() {
    private val _state: MutableStateFlow<NetworkResult<Unit>?> = MutableStateFlow(null)
    val state: StateFlow<NetworkResult<Unit>?> = _state

    private var loginJob: Job? = null

    fun login(username: String, password: String) {
        if (loginJob?.isActive != true) {
            loginJob = viewModelScope.launch {
                _state.emit(NetworkResult.Loading)
                delay(2000)
                if (username == USERNAME && password == PASSWORD)
                    _state.emit(success())
                else
                    _state.emit(failure(IllegalStateException("Invalid username or password")))
            }
        }
    }

    override fun onCleared() {
        loginJob?.cancel()
    }
}