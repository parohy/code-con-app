@file:OptIn(ExperimentalCoroutinesApi::class)

package sk.parohy.codecon.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import sk.parohy.codecon.api.model.God

data class AppState(
    val loginState: NetworkResult<Unit>? = null,
    val godsOfCode: NetworkResult<List<God>>? = null
)

private const val USERNAME = "username"
private const val PASSWORD = "password"

object Api {
    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    val state: Flow<AppState> = _appState

    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }

    suspend fun signIn(username: String, password: String) {
        _appState.emit(_appState.value.copy(loginState = NetworkResult.Loading))
        delay(2000)
        if (username == USERNAME && password == PASSWORD)
            _appState.emit(_appState.value.copy(loginState = success()))
        else
            _appState.emit(_appState.value.copy(loginState = failure(IllegalStateException("Invalid username or password"))))
    }

    suspend fun fetchGods() {
        _appState.emit(_appState.value.copy(godsOfCode = NetworkResult.Loading))
        val firebaseFlow: Flow<NetworkResult<List<God>>> = callbackFlow {
            firestore.collection("gods")
                .get()
                .addOnCompleteListener { task ->
                    val result = if (task.isSuccessful)
                        success(task.result.documents.mapNotNull { it.toObject(God::class.java) })
                    else
                        failure(IllegalStateException("Failed to load data"))

                    trySend(result)
                }

            awaitClose {}
        }

        _appState.emit(_appState.value.copy(godsOfCode = firebaseFlow.first()))
    }
}