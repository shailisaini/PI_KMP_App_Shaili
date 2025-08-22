package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getAuthViewModel: AuthenticationUsesCases,
    private val localData: LocalDataSource,
    private val connectivityObserver: ConnectivityObserver, //  to check network
) : ViewModel() {
    var noInternetConnection: String = "No Internet Found!"
    var somethingWentWrong: String = "Something went wrong"

    // check if which API has to call if internet comes
    private var shouldRefreshLanguages = false
    private var shouldRefreshUserType = false

    fun isNetworkAvailable(): Boolean {
        return connectivityObserver.getCurrentStatus() == ConnectivityObserver.Status.Available
    }

    val _uiState = MutableStateFlow(UiState<GetLanguageListResponse>())
    val uiState: StateFlow<UiState<GetLanguageListResponse>> = _uiState

    private val _uiStateUserType = MutableStateFlow(UiState<GetUserTypeResponse>())
    val uiStateType: StateFlow<UiState<GetUserTypeResponse>> = _uiStateUserType

    private val _uiStateValidateUser = MutableStateFlow(UiState<ValidateUserResponse>())
    val validateUserResponse: StateFlow<UiState<ValidateUserResponse>> = _uiStateValidateUser

    private val _uiStateSendOtp = MutableStateFlow(UiState<SendOTPResponse>())
    val uiStateSendOtpResponse: StateFlow<UiState<SendOTPResponse>> = _uiStateSendOtp

    private val _uiStateLogin = MutableStateFlow(UiState<LoginApiResponse>())
    val uiStateLoginResponse: StateFlow<UiState<LoginApiResponse>> = _uiStateLogin

    private val viewUserProfile = MutableStateFlow(UiState<LoginApiResponse>())
    val viewUserProfileResponse: StateFlow<UiState<LoginApiResponse>> = viewUserProfile

    private val verifyLogin = MutableStateFlow(UiState<VerifyOtpResponse>())
    val verifyLoginResponse: StateFlow<UiState<VerifyOtpResponse>> = verifyLogin

    private val forgetPassword = MutableStateFlow(UiState<ForgetPasswordResponse>())
    val forgetPasswordResponse: StateFlow<UiState<ForgetPasswordResponse>> = forgetPassword

    private val createRegPassword = MutableStateFlow(UiState<CreateRegisterPasswordResponse>())
    val createRegPasswordResponse: StateFlow<UiState<CreateRegisterPasswordResponse>> =
        createRegPassword

    private val firstStepProfilePassword =
        MutableStateFlow(UiState<CreateFirstStepProfileResponse>())
    val firstStepProfilePasswordResponse: StateFlow<UiState<CreateFirstStepProfileResponse>> =
        firstStepProfilePassword

    private val query = MutableStateFlow("")

    // getting userName from intent

    private val userName = MutableStateFlow<String?>(null)
    val userNameValue: String? get() = userName.value

    fun saveUserName(number: String) {
        userName.value = number
    }

    fun savePrefData(key: String, value: String) {
        localData.saveValue(key, value)
    }

    fun getPrefData(key: String): String {
        return localData.getValue(key, "N/A")
    }

    init {
        observeNetworkChangesAndRefresh()
        viewModelScope.launch {
            query.debounce(1000)
                .filter { it.isNotEmpty() }
                .collectLatest {
                    // Your logic
                }
            // it can be use for search
            /*.collectLatest { queryText ->
                    getSearchResults(queryText)
                }*/
        }
    }

    // observer function for data syncing
    private fun observeNetworkChangesAndRefresh() {
        viewModelScope.launch {
            var wasPreviouslyOffline = !isNetworkAvailable()

            connectivityObserver.observe()
                .distinctUntilChanged()
                .collect { status ->
                    val isNowOnline = status == ConnectivityObserver.Status.Available

                    if (wasPreviouslyOffline && isNowOnline) {
                        LoggerProvider.logger.d("Reconnected - checking which APIs to retry")

                        if (shouldRefreshLanguages) getLanguages()
                        if (shouldRefreshUserType) getUserType()

                    }

                    wasPreviouslyOffline = !isNowOnline
                }
        }
    }

    fun getLanguages() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshLanguages = true
            _uiState.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshLanguages = false // Reset on successful start

        _uiState.update { it.copy(isLoading = true, error = "") }

        getAuthViewModel.getLanguage()
            .catch { exception ->
                _uiState.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiState.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiState.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getUserType() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshUserType = true
            _uiStateUserType.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshUserType = false

        _uiStateUserType.update { UiState(isLoading = true) }
        getAuthViewModel.getUserType()
            .catch { exception ->
                _uiStateUserType.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateUserType.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateUserType.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getValidateUser(userName: String, userTypeId: String) = viewModelScope.launch {
        // no need to sync data

        _uiStateValidateUser.update { UiState(isLoading = true) }
        getAuthViewModel.getValidateUserCase(userName, userTypeId)
            .catch { exception ->
                _uiStateValidateUser.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateValidateUser.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateValidateUser.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun loginWithPasswordViewModel(loginRequest: LoginRequest) = viewModelScope.launch {
//        no need to data sync
        _uiStateLogin.update { UiState(isLoading = true) }
        getAuthViewModel.getUserLoginPassword(loginRequest)
            .catch { exception ->
                _uiStateLogin.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateLogin.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateLogin.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getOTPViewModel(mobNo: String) = viewModelScope.launch {
//        no need to data sync
        _uiStateSendOtp.update { UiState(isLoading = true) }
        getAuthViewModel.getOtpOnCall(mobNo)
            .catch { exception ->
                _uiStateSendOtp.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateSendOtp.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateSendOtp.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getOTPWhatsappViewModel(mobNo: String) = viewModelScope.launch {
        // no need to sync data

        _uiStateSendOtp.update { UiState(isLoading = true) }
        getAuthViewModel.getOTPOnWhatsapp(mobNo)
            .catch { exception ->
                _uiStateSendOtp.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateSendOtp.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateSendOtp.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun forgetPassword(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ) = viewModelScope.launch {
        forgetPassword.update { UiState(isLoading = true) }
        getAuthViewModel.forgetPassword(passwordRequest, strToken)
            .catch { exception ->
                forgetPassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        forgetPassword.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        forgetPassword.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun createRegisterPassword(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ) = viewModelScope.launch {
        createRegPassword.update { UiState(isLoading = true) }
        getAuthViewModel.createRegisterPassword(passwordRequest, strToken)
            .catch { exception ->
                createRegPassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        createRegPassword.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        createRegPassword.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getVerifyOtpViewModel(mobNo: String, otpValue: String) = viewModelScope.launch {
        // no need to sync data

        verifyLogin.update { UiState(isLoading = true) }
        getAuthViewModel.getVerifyOtp(mobNo, otpValue)
            .catch { exception ->
                verifyLogin.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        verifyLogin.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        verifyLogin.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getLoginWithOtpViewModel(request: LoginWithOtpRequest) = viewModelScope.launch {
        // no need to sync data

        _uiStateLogin.update { UiState(isLoading = true) }
        getAuthViewModel.getLoginWithOtp(request)
            .catch { exception ->
                _uiStateLogin.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiStateLogin.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        _uiStateLogin.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun getUserProfileViewModel(data: String) = viewModelScope.launch {
        // no need to sync data
        viewUserProfile.update { UiState(isLoading = true) }
        getAuthViewModel.getViewUserProfile(data)
            .catch { exception ->
                viewUserProfile.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        viewUserProfile.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        viewUserProfile.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }

    fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
    ) = viewModelScope.launch {
        firstStepProfilePassword.update { UiState(isLoading = true) }
        getAuthViewModel.createFirstStepProfileRepo(firstStepProfileRequest, strToken)
            .catch { exception ->
                firstStepProfilePassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        firstStepProfilePassword.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        firstStepProfilePassword.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                )
            }
    }
}

data class UiState<T>(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: T? = null,
)