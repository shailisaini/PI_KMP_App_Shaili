package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.domain.useCases.GetLanguageUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getLanguageUsesCases: GetLanguageUsesCases
):ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query.debounce(1000)
                .filter { it.isNotEmpty() }
                .collectLatest { }

        }
    }

    fun getLanguages(page: String, limit: String) = viewModelScope.launch {
        _uiState.update { UiState(isLoading = true) }
       val response = getLanguageUsesCases(page, limit)
        if (response.isSuccess){
            _uiState.update { UiState(success = response.getOrThrow() ) }
        }
        else{
            _uiState.update { UiState(error = response.exceptionOrNull()?.message.toString() ) }
        }
    }
}

data class UiState(
    val isLoading : Boolean = false,
    val error : String = "",
    val success : GetLanguageListResponse? = null
)