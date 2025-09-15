package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForceUpdateResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ProfessionListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.QualificationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ReasonListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SpecializationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
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
    var serverError: String = "Failed to connect to"
    var serverMsg: String = "Server not responding!"

    // check if which API has to call if internet comes
    private var shouldRefreshLanguages = false
    private var shouldRefreshUserType = false
    private var shouldRefreshProfile = false
    private var shouldRefreshState = false
    private var shouldRefreshProfessional = false
    private var shouldRefreshSpecialization = false
    private var shouldRefreshQualification = false
    private var shouldRefreshAllReason = false

    fun isNetworkAvailable(): Boolean {
        return connectivityObserver.getCurrentStatus() == ConnectivityObserver.Status.Available
    }

    val _uiState = MutableStateFlow(UiState<GetLanguageListResponse>())
    val uiState: StateFlow<UiState<GetLanguageListResponse>> = _uiState

    val _uiStateUserType = MutableStateFlow(UiState<GetUserTypeResponse>())
    val uiStateType: StateFlow<UiState<GetUserTypeResponse>> = _uiStateUserType

    val _uiStateValidateUser = MutableStateFlow(UiState<ValidateUserResponse>())
    val validateUserResponse: StateFlow<UiState<ValidateUserResponse>> = _uiStateValidateUser

    val _uiStateSendOtp = MutableStateFlow(UiState<SendOTPResponse>())
    val uiStateSendOtpResponse: StateFlow<UiState<SendOTPResponse>> = _uiStateSendOtp

    private val _uiStateLogin = MutableStateFlow(UiState<LoginApiResponse>())
    val uiStateLoginResponse: StateFlow<UiState<LoginApiResponse>> = _uiStateLogin

    val loginWithOtpState = MutableStateFlow(UiState<LoginApiResponse>())
    val loginWithOtpResponse: StateFlow<UiState<LoginApiResponse>> = loginWithOtpState

    val viewUserProfile = MutableStateFlow(UiState<ViewProfileResponse>())
    val viewUserProfileResponse: StateFlow<UiState<ViewProfileResponse>> = viewUserProfile

    val verifyLogin = MutableStateFlow(UiState<VerifyOtpResponse>())
    val verifyLoginResponse: StateFlow<UiState<VerifyOtpResponse>> = verifyLogin

    val forgetPassword = MutableStateFlow(UiState<ForgetPasswordResponse>())
    val forgetPasswordResponse: StateFlow<UiState<ForgetPasswordResponse>> = forgetPassword

    val createRegPassword = MutableStateFlow(UiState<CreateRegisterPasswordResponse>())
    val createRegPasswordResponse: StateFlow<UiState<CreateRegisterPasswordResponse>> =
        createRegPassword

    val firstStepProfilePassword =
        MutableStateFlow(UiState<CreateFirstStepProfileResponse>())
    val firstStepProfilePasswordResponse: StateFlow<UiState<CreateFirstStepProfileResponse>> =
        firstStepProfilePassword

    val allStates = MutableStateFlow(UiState<List<StateListResponse>>())
    val allStatesResponse: StateFlow<UiState<List<StateListResponse>>> = allStates

    val allDistricts = MutableStateFlow(UiState<List<DistrictListResponse>>())
    val allDistrictsResponse: StateFlow<UiState<List<DistrictListResponse>>> = allDistricts

    val allBlocks = MutableStateFlow(UiState<List<BlockListResponse>>())
    val allBlocksResponse: StateFlow<UiState<List<BlockListResponse>>> = allBlocks

    val allSchools = MutableStateFlow(UiState<SchoolListResponse>())
    val allSchoolsResponse: StateFlow<UiState<SchoolListResponse>> = allSchools

    val allUdiseCode = MutableStateFlow(UiState<SchoolByUdiseCodeResponse>())
    val allUdiseCodeResponse: StateFlow<UiState<SchoolByUdiseCodeResponse>> = allUdiseCode

    val professionalProfile = MutableStateFlow(UiState<CreateFirstStepProfileResponse>())
    val professionalProfileResponse: StateFlow<UiState<CreateFirstStepProfileResponse>> =
        professionalProfile

    val professionList = MutableStateFlow(UiState<List<ProfessionListResponse>>())
    val professionListResponse: StateFlow<UiState<List<ProfessionListResponse>>> = professionList

    val qualificationList = MutableStateFlow(UiState<List<QualificationListResponse>>())
    val qualificationListResponse: StateFlow<UiState<List<QualificationListResponse>>> =
        qualificationList

    val specializationList = MutableStateFlow(UiState<List<SpecializationListResponse>>())
    val specializationListResponse: StateFlow<UiState<List<SpecializationListResponse>>> =
        specializationList

    val reasonList = MutableStateFlow(UiState<ReasonListResponse>())
    val reasonListResponse: StateFlow<UiState<ReasonListResponse>> = reasonList

    val forceUpdate = MutableStateFlow(UiState<ForceUpdateResponse>())
    val forceUpdateResponse: StateFlow<UiState<ForceUpdateResponse>> = forceUpdate

    private val query = MutableStateFlow("")

    // getting userName from intent

    private val userName = MutableStateFlow<String?>(null)
    val userNameValue: String? get() = userName.value

    private val firstName = MutableStateFlow<String?>(null)
    val firstNameValue: String? get() = firstName.value

    private val lastName = MutableStateFlow<String?>(null)
    val lastNameValue: String? get() = lastName.value

    private val userId = MutableStateFlow<String?>(null)
    val userIdValue: String? get() = userId.value

    fun saveUserId(userIds: String) {
        userId.value = userIds
    }

    fun saveUserName(number: String) {
        userName.value = number
    }

    fun saveFirstName(number: String) {
        firstName.value = number
    }

    fun saveLastName(number: String) {
        lastName.value = number
    }

    fun savePrefData(key: String, value: String) {
        localData.saveValue(key, value)
    }

    fun getPrefData(key: String): String {
        return localData.getValue(key, "")
    }

    // remove particular key
    fun clearPrefValue(key: String) {
        localData.clearValue(key)
    }

    // clear all data
    fun clearPref() {
        localData.clearAll()
    }

    init {
        observeNetworkChangesAndRefresh()
        viewModelScope.launch {
            query.debounce(1000).filter { it.isNotEmpty() }.collectLatest {
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

            connectivityObserver.observe().distinctUntilChanged().collect { status ->
                val isNowOnline = status == ConnectivityObserver.Status.Available

                if (wasPreviouslyOffline && isNowOnline) {
                    logger.d("Reconnected - checking which APIs to retry")

                    if (shouldRefreshLanguages) getLanguages()
                    if (shouldRefreshUserType) getUserType()
                    if (shouldRefreshState) getAllStateList()
                    if (shouldRefreshProfile) getUserProfileViewModel(
                        localData.getValue("user_token"), localData.getValue("userName")
                    )
                    if (shouldRefreshProfessional) getAllProfessionRepo()
                    if (shouldRefreshQualification) getAllStateList()
                    if (shouldRefreshAllReason) getAllReasonRepo()

                }

                wasPreviouslyOffline = !isNowOnline
            }
        }
    }
// reset states
fun <T> resetState(state: MutableStateFlow<UiState<T>>) {
        state.value = UiState()
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

        _uiState.update { it.copy(isLoading = true) }

        getAuthViewModel.getLanguage().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiState.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiState.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                _uiState.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiState.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiState.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
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
        getAuthViewModel.getUserType().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiStateUserType.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiStateUserType.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                _uiStateUserType.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiStateUserType.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiStateUserType.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getValidateUser(userName: String, userTypeId: String) = viewModelScope.launch {
        // no need to sync data
        _uiStateValidateUser.update { UiState(isLoading = true) }
        getAuthViewModel.getValidateUserCase(userName, userTypeId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiStateValidateUser.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiStateValidateUser.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                _uiStateValidateUser.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiStateValidateUser.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiStateValidateUser.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun loginWithPasswordViewModel(loginRequest: LoginRequest) = viewModelScope.launch {
//        no need to data sync
        _uiStateLogin.update { UiState(isLoading = true) }
        getAuthViewModel.getUserLoginPassword(loginRequest).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiStateLogin.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiStateLogin.update {
                    UiState(error = exception.message?.takeIf { it.isNotBlank() }
                        ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                if (data.status == true) { // success field from API
                    _uiStateLogin.update { UiState(success = data) }
                } else {
                    _uiStateLogin.update {
                        UiState(error = data.message?.takeIf { it.isNotBlank() }
                            ?: somethingWentWrong)
                    }
                }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiStateLogin.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiStateLogin.update {
                        UiState(error = exception.message?.takeIf { it.isNotBlank() }
                            ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getOTPViewModel(mobNo: String) = viewModelScope.launch {
//        no need to data sync
        _uiStateSendOtp.update { UiState(isLoading = true) }
        getAuthViewModel.getOtpOnCall(mobNo).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiStateSendOtp.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiStateSendOtp.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                _uiStateSendOtp.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiStateSendOtp.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiStateSendOtp.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getOTPWhatsappViewModel(mobNo: String) = viewModelScope.launch {
        // no need to sync data

        _uiStateSendOtp.update { UiState(isLoading = true) }
        getAuthViewModel.getOTPOnWhatsapp(mobNo).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                _uiStateSendOtp.update {
                    UiState(error = serverMsg)
                }
            } else {
                _uiStateSendOtp.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                _uiStateSendOtp.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    _uiStateSendOtp.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    _uiStateSendOtp.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun forgetPassword(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ) = viewModelScope.launch {
        forgetPassword.update { UiState(isLoading = true) }
        getAuthViewModel.forgetPassword(passwordRequest, strToken).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                forgetPassword.update {
                    UiState(error = serverMsg)
                }
            } else {
                forgetPassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                forgetPassword.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    forgetPassword.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    forgetPassword.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun createRegisterPassword(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ) = viewModelScope.launch {
        createRegPassword.update { UiState(isLoading = true) }
        getAuthViewModel.createRegisterPassword(passwordRequest, strToken).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                createRegPassword.update {
                    UiState(error = serverMsg)
                }
            } else {
                createRegPassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                createRegPassword.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    createRegPassword.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    createRegPassword.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getVerifyOtpViewModel(mobNo: String, otpValue: String) = viewModelScope.launch {
        // no need to sync data

        verifyLogin.update { UiState(isLoading = true) }
        getAuthViewModel.getVerifyOtp(mobNo, otpValue).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                verifyLogin.update {
                    UiState(error = serverMsg)
                }
            } else {
                verifyLogin.update {
                    logger.d("VerifyOtp Error1: ${exception.message}")
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                verifyLogin.update { UiState(success = data) }
                logger.d("VerifyOtp Error2: ${data}")
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    verifyLogin.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    verifyLogin.update {
                        logger.d("VerifyOtp Error3: ${exception.message}")
                        UiState(error = exception.message ?: somethingWentWrong)

                    }
                }
            })
        }
    }

    fun getLoginWithOtpViewModel(request: LoginWithOtpRequest) = viewModelScope.launch {
        // no need to sync data

        loginWithOtpState.update { UiState(isLoading = true) }
        getAuthViewModel.getLoginWithOtp(request).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                loginWithOtpState.update {
                    UiState(error = serverMsg)
                }
            } else {
                loginWithOtpState.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                loginWithOtpState.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    loginWithOtpState.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    loginWithOtpState.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getUserProfileViewModel(token: String, data: String) = viewModelScope.launch {
        // with sync data
        if (!isNetworkAvailable()) {
            shouldRefreshProfile = true
            viewUserProfile.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshProfile = false // Reset on successful start

        viewUserProfile.update { it.copy(isLoading = true, error ="") }

        getAuthViewModel.getViewUserProfile(token, data)
            .catch { exception ->
                // Handle exception thrown before collect
                if (exception.message?.contains(serverError) == true) {
                    viewUserProfile.update {
                        UiState(error = serverMsg.takeIf { it.isNotBlank() } ?: somethingWentWrong)
                    }
                } else {
                    viewUserProfile.update {
                        UiState(error = exception.message?.takeIf { it.isNotBlank() }
                            ?: somethingWentWrong)
                    }
                }
            }
            .collect { result ->
                result.fold(
                    onSuccess = { data ->
                        viewUserProfile.update { UiState(success = data) }
                    },
                    onFailure = { exception ->
                        // Handle exception inside Result
                        if (exception.message?.contains(serverError) == true) {
                            viewUserProfile.update {
                                UiState(error = serverMsg.takeIf { it.isNotBlank() }
                                    ?: somethingWentWrong)
                            }
                        } else {
                            viewUserProfile.update {
                                UiState(error = exception.message?.takeIf { it.isNotBlank() }
                                    ?: somethingWentWrong)
                            }
                        }
                    }
                )
            }
    }

    fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null,
    ) = viewModelScope.launch {
        firstStepProfilePassword.update { UiState(isLoading = true) }
        getAuthViewModel.createFirstStepProfileRepo(
            firstStepProfileRequest, strToken, profilePic, fileName
        ).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                firstStepProfilePassword.update {
                    UiState(error = serverMsg)
                }
            } else {
                firstStepProfilePassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                firstStepProfilePassword.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    firstStepProfilePassword.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    firstStepProfilePassword.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllStateList() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshState = true
            allStates.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshState = false // Reset on successful start

        allStates.update { it.copy(isLoading = true, error = "") }

        getAuthViewModel.getAllStateListRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                allStates.update {
                    UiState(error = serverMsg)
                }
            } else {
                allStates.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                allStates.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    allStates.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    allStates.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllDistrictByStateId(stateId: Int) = viewModelScope.launch {
        allDistricts.update { UiState(isLoading = true) }
        getAuthViewModel.getAllDistrictByStateIdRepo(stateId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                allDistricts.update {
                    UiState(error = serverMsg)
                }
            } else {
                allDistricts.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                allDistricts.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    allDistricts.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    allDistricts.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllBlockByDistrictId(districtId: Int) = viewModelScope.launch {
        allBlocks.update { UiState(isLoading = true) }
        getAuthViewModel.getAllBlockByDistrictIdRepo(districtId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                allBlocks.update {
                    UiState(error = serverMsg)
                }
            } else {
                allBlocks.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                allBlocks.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    allBlocks.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    allBlocks.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllSchoolsByBlockId(blockId: Int) = viewModelScope.launch {
        allSchools.update { UiState(isLoading = true) }
        getAuthViewModel.getAllSchoolsByBlockIdRepo(blockId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                allSchools.update {
                    UiState(error = serverMsg)
                }
            } else {
                allSchools.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                allSchools.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    allSchools.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    allSchools.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllDetailsByUdiseId(udiseCode: String) = viewModelScope.launch {
        allUdiseCode.update { UiState(isLoading = true) }
        getAuthViewModel.getAllDetailsByUdiseIdRepo(udiseCode).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                allUdiseCode.update {
                    UiState(error = serverMsg)
                }
            } else {
                allUdiseCode.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                allUdiseCode.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    allUdiseCode.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    allUdiseCode.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ) = viewModelScope.launch {
        professionalProfile.update { UiState(isLoading = true) }
        getAuthViewModel.createProfessionalProfileRepo(professionalProfileRequest, strToken)
            .catch { exception ->
                if (exception.message?.contains(serverError) == true) {
                    professionalProfile.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    professionalProfile.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            }.collect { result ->
                result.fold(onSuccess = { data ->
                    professionalProfile.update { UiState(success = data) }
                }, onFailure = { exception ->
                    if (exception.message?.contains(serverError) == true) {
                        professionalProfile.update {
                            UiState(error = serverMsg)
                        }
                    } else {
                        professionalProfile.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                })
            }
    }

    fun getAllProfessionRepo() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshProfessional = true
            professionList.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshProfessional = false // Reset on successful start

        professionList.update { it.copy(isLoading = true, error = "") }

        getAuthViewModel.getAllProfessionRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                professionList.update {
                    UiState(error = serverMsg)
                }
            } else {
                professionList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                professionList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    professionList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    professionList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllQualificationRepo(profession: Int) = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshQualification = true
            qualificationList.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshQualification = false // Reset on successful start

        qualificationList.update { it.copy(isLoading = true, error = "") }

        getAuthViewModel.getAllQualificationRepo(profession).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                qualificationList.update {
                    UiState(error = serverMsg)
                }
            } else {
                qualificationList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                qualificationList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    qualificationList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    qualificationList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int,
    ) = viewModelScope.launch {

        specializationList.update { it.copy(isLoading = true, error = "") }

        getAuthViewModel.getAllSpecializationRepo(profession, qualification).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                specializationList.update {
                    UiState(error = serverMsg)
                }
            } else {
                specializationList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                specializationList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    specializationList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    specializationList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllReasonRepo() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshAllReason = true
            reasonList.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshAllReason = false // Reset on successful start

        reasonList.update { it.copy(isLoading = true, error = "") }
        getAuthViewModel.getAllReasonRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                reasonList.update {
                    UiState(error = serverMsg)
                }
            } else {
                reasonList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                reasonList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    reasonList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    reasonList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getForceUpdateApp(deviceOsVersion: Double, latestAppVersion: Double) =
        viewModelScope.launch {
            forceUpdate.update { UiState(isLoading = true) }
            getAuthViewModel.getForceUpdateAppRepo(deviceOsVersion, latestAppVersion)
                .catch { exception ->
                    if (exception.message?.contains(serverError) == true) {
                        forceUpdate.update {
                            UiState(error = serverMsg)
                        }
                    } else {
                        forceUpdate.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                }.collect { result ->
                    result.fold(onSuccess = { data ->
                        forceUpdate.update { UiState(success = data) }
                    }, onFailure = { exception ->
                        if (exception.message?.contains(serverError) == true) {
                            forceUpdate.update {
                                UiState(error = serverMsg)
                            }
                        } else {
                            forceUpdate.update {
                                UiState(error = exception.message ?: somethingWentWrong)
                            }
                        }
                    })
                }
        }
}

data class UiState<T>(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: T? = null,
)