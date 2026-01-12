package io.github.amanbutnot.moosic.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.amanbutnot.moosic.data.model.PingResponse
import io.github.amanbutnot.moosic.network.PingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for handling the ping operation to the server.
 *
 * @property repository The [PingRepository] used to perform the ping network call.
 */
class PingViewModel(
    private val repository: PingRepository = PingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PingUiState>(PingUiState.Idle)

    /**
     * Exposes the current state of the ping operation as a [StateFlow].
     */
    val uiState: StateFlow<PingUiState> = _uiState.asStateFlow()

    /**
     * Performs a ping operation with the provided credentials.
     * Updates [uiState] to [PingUiState.Loading] during the process and then to
     * either [PingUiState.Success] or [PingUiState.Error] based on the result.
     *
     * @param baseUrl The base URL of the server.
     * @param username The username for authentication.
     * @param password The password for authentication.
     */
    fun ping(baseUrl: String, username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = PingUiState.Loading
            try {
                val response = repository.ping(baseUrl = baseUrl, username = username, password = password)
                if (response != null) {
                    if (response.subsonicResponse.status == "ok") {
                        _uiState.value = PingUiState.Success(response)
                    } else {
                        _uiState.value =
                            PingUiState.Error("Server returned status: ${response.subsonicResponse.status}")
                    }
                } else {
                    _uiState.value = PingUiState.Error("Failed to receive response from server")
                }
            } catch (e: Exception) {
                _uiState.value = PingUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Resets the [uiState] to [PingUiState.Idle].
     */
    fun resetState() {
        _uiState.value = PingUiState.Idle
    }
}

/**
 * Represents the different states of the ping operation.
 */
sealed class PingUiState {
    /**
     * Initial state before any ping operation is started or after a reset.
     */
    data object Idle : PingUiState()

    /**
     * State indicating that a ping operation is currently in progress.
     */
    data object Loading : PingUiState()

    /**
     * State indicating that the ping operation was successful.
     *
     * @property data The [PingResponse] received from the server.
     */
    data class Success(val data: PingResponse) : PingUiState()

    /**
     * State indicating that the ping operation failed.
     *
     * @property message A description of the error that occurred.
     */
    data class Error(val message: String) : PingUiState()
}
