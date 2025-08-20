package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getLanguageUsesCases: AuthenticationUsesCases
):ViewModel() {
//    private val _uiState = MutableStateFlow(UiState())
//    val uiState = _uiState.asStateFlow()

    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query.debounce(1000)
                .filter { it.isNotEmpty() }
                .collectLatest { }

        }
    }


}

