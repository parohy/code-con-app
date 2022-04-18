package sk.parohy.codecon.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sk.parohy.codecon.api.Api
import sk.parohy.codecon.api.NetworkResult

class LoginViewModel: ViewModel() {
    val state: Flow<NetworkResult<String>?> = Api.state.map { it.loginState }

    private var loginJob: Job? = null

    fun login(username: String, password: String, pref: SharedPreferences) {
        if (loginJob?.isActive != true) {
            loginJob = viewModelScope.launch {
                Api.signIn(username, password, pref)
            }
        }
    }

    override fun onCleared() {
        loginJob?.cancel()
    }
}