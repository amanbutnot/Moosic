package io.github.amanbutnot.moosic.business

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.amanbutnot.moosic.data.model.AlbumListResponse
import io.github.amanbutnot.moosic.network.getAlbumList
import kotlinx.coroutines.launch

class AlbumListViewModel(
    sort: String = "newest",
    size: Int = 10,
    offset: Int = 0
) : ViewModel() {
    private val _state = mutableStateOf(DataState())
    val state: MutableState<DataState> = _state

    data class DataState(
        val success: Boolean = false,
        val loading: Boolean = false,
        val data: AlbumListResponse? = null,
        val message: String? = null,
    )

    init {
        getDataState(sort, size, offset)
    }

    fun getDataState(
        sort: String,
        size: Int,
        offset: Int
    ) {
        viewModelScope.launch {
            _state.value = DataState(loading = true)
            try {
                val albumResponse = getAlbumList(sort, size, offset)
                _state.value = DataState(
                    success = true,
                    loading = false,
                    data = albumResponse,
                    message = "Album List Fetched Successfully"
                )
            } catch (e: Exception) {
                _state.value = DataState(
                    loading = false,
                    success = false,
                    data = null,
                    message = e.message,
                )
            }
        }
    }
}

