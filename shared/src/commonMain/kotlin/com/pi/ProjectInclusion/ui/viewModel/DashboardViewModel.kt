package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import com.pi.ProjectInclusion.domain.useCases.DashboardUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getUsesCases: DashboardUsesCases,
    private val localData: LocalDataSource,
    private val connectivityObserver: ConnectivityObserver, //  to check network
) : ViewModel() {

    var noInternetConnection: String = "No Internet Found!"
    var somethingWentWrong: String = "Something went wrong"

    // check if which API has to call if internet comes
    private var shouldRefreshLanguages = false
    private var shouldRefreshUserType = false

    private val query = MutableStateFlow("")

    private val getCertificate = MutableStateFlow(UiState<CertificateListResponse>())
    val getCertificateResponse: StateFlow<UiState<CertificateListResponse>> = getCertificate

    fun isNetworkAvailable(): Boolean {
        return connectivityObserver.getCurrentStatus() == ConnectivityObserver.Status.Available
    }

    init {
        viewModelScope.launch {
            query.debounce(1000)
                .filter { it.isNotEmpty() }
                .collectLatest { }
        }
    }

    fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ) = viewModelScope.launch {
        getCertificate.update { UiState(isLoading = true) }
        getUsesCases.getLMSUserCertificate(certificateRequest, strToken)
            .catch { exception ->
                getCertificate.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        getCertificate.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        getCertificate.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

}