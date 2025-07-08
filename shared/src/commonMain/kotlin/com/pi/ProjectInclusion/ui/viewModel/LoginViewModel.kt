package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.useCases.GetLanguageUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getLanguageUsesCases: GetLanguageUsesCases,
    private val localData: LocalDataSource
):ViewModel() {
    private val _uiState = MutableStateFlow(UiState<GetLanguageListResponse>())
    val uiState = _uiState.asStateFlow()

//    private val _uiStateUserType = MutableStateFlow(UiState<List<GetUserTypeResponse>>())
    private val _uiStateUserType = MutableStateFlow(UiState<GetUserTypeResponse>())
    val uiStateType = _uiStateUserType.asStateFlow()

    private val query = MutableStateFlow("")

    fun savePrefData(key: String, value: String) {
        localData.saveValue(key, value)
    }

    fun getPrefData(key: String): String {
        return localData.getValue(key, "")
    }

    init {
        viewModelScope.launch {
            query.debounce(1000)
                .filter { it.isNotEmpty() }
                .collectLatest { }

        }
    }

    fun getLanguages(page: String, limit: String) = viewModelScope.launch {
        _uiState.update { UiState(isLoading = true) }
       val response = getLanguageUsesCases.getLanguage(page, limit)
        if (response.isSuccess){
            _uiState.update { UiState(success = response.getOrThrow() ) }
        }
        else{
            _uiState.update { UiState(error = response.exceptionOrNull()?.message.toString() ) }
        }
    }
    fun getUserType() = viewModelScope.launch {
        _uiStateUserType.update { UiState(isLoading = true) }
        val response = getLanguageUsesCases.getUserType()
        if (response.isSuccess){
            LoggerProvider.logger.d("Screen: "+"UserTypeScreen()"+response)
            _uiStateUserType.update { UiState(success = response.getOrThrow() ) }
        }
        else{
            _uiStateUserType.update { UiState(error = response.exceptionOrNull()?.message.toString() ) }
            LoggerProvider.logger.d("Screen: "+"UserTypeScreen()"+response.exceptionOrNull())
        }
    }
}

data class UiState<T>(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: T? = null
)