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
import sk.parohy.codecon.api.model.God

class HomeViewModel: ViewModel() {
    val state: Flow<NetworkResult<List<God>>?> = Api.state.map { it.godsOfCode }

    private var fetchJob: Job? = null

    fun refresh() {
        if (fetchJob?.isActive != true)
            fetchJob = viewModelScope.launch {
                Api.fetchGods()
            }
    }

    fun logOut(pref: SharedPreferences) {
        Api.signOut(pref)
    }
}