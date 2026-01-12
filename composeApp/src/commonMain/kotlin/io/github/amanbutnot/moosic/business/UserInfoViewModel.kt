package io.github.amanbutnot.moosic.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.amanbutnot.moosic.data.model.UserInfoModel
import io.github.amanbutnot.moosic.network.UserInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for fetching and holding user information.
 *
 * @property repository The [UserInfoRepository] used to fetch user data.
 */
class UserInfoViewModel(
    private val repository: UserInfoRepository = UserInfoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Idle)

    /**
     * Exposes the current state of the user info request.
     */
    val uiState: StateFlow<UserInfoUiState> = _uiState.asStateFlow()

    /**
     * Fetches user information using the repository.
     * Updates [uiState] to reflect the current progress and result.
     */
    fun getUserInfo() {
        viewModelScope.launch {
            _uiState.value = UserInfoUiState.Loading
            try {
                val userInfo = repository.getUserInfo()
                if (userInfo != null) {
                    _uiState.value = UserInfoUiState.Success(userInfo)
                } else {
                    _uiState.value = UserInfoUiState.Error("Failed to fetch user information.")
                }
            } catch (e: Exception) {
                _uiState.value = UserInfoUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Resets the [uiState] to [UserInfoUiState.Idle].
     */
    fun resetState() {
        _uiState.value = UserInfoUiState.Idle
    }
}

/**
 * Represents the various states of fetching user information.
 */
sealed class UserInfoUiState {
    /**
     * Initial state before the request starts.
     */
    data object Idle : UserInfoUiState()

    /**
     * State while user information is being fetched.
     */
    data object Loading : UserInfoUiState()

    /**
     * State when user information is successfully fetched.
     *
     * @property data The [UserInfoModel] containing the user's details.
     */
    data class Success(val data: UserInfoModel) : UserInfoUiState()

    /**
     * State when fetching user information fails.
     *
     * @property message A description of the error.
     */
    data class Error(val message: String) : UserInfoUiState()
}
