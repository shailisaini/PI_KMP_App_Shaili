package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.AccountDeleteResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CheckProfileCompletionResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.FAQsListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.NotificationResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryByCategoryIdResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.TokenResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingTokenResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingsJoinResponse
import com.pi.ProjectInclusion.data.model.profileModel.ChangePasswordRequest
import com.pi.ProjectInclusion.data.model.profileModel.ProfileNameChangeRequest
import com.pi.ProjectInclusion.data.model.profileModel.response.ChangeRequestResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.TrackRequestResponse
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import com.pi.ProjectInclusion.domain.useCases.DashboardUsesCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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
    var serverError: String = "Failed to connect to"
    var serverMsg: String = "Server not responding!"

    // check if which API has to call if internet comes
    private var shouldRefreshLanguages = false
    private var shouldRefreshAllCategory = false
    private var shouldRefreshRefreshToken = false

    private val query = MutableStateFlow("")

    val getCertificate = MutableStateFlow(UiState<CertificateListResponse>())
    val getCertificateResponse: StateFlow<UiState<CertificateListResponse>> = getCertificate

    val getNotification = MutableStateFlow(UiState<NotificationResponse>())
    val getNotificationResponse: StateFlow<UiState<NotificationResponse>> = getNotification

    val getCategoryList = MutableStateFlow(UiState<List<CategoryListResponse>>())
    val getCategoryListResponse: StateFlow<UiState<List<CategoryListResponse>>> = getCategoryList

    val getSubCategoryList = MutableStateFlow(UiState<List<SubCategoryListResponse>>())
    val getSubCategoryListResponse: StateFlow<UiState<List<SubCategoryListResponse>>> =
        getSubCategoryList

    val getSubCategoryByCategoryIdList =
        MutableStateFlow(UiState<SubCategoryByCategoryIdResponse>())
    val getSubCategoryByCategoryIdListResponse: StateFlow<UiState<SubCategoryByCategoryIdResponse>> =
        getSubCategoryByCategoryIdList

    val getFAQsList = MutableStateFlow(UiState<FAQsListResponse>())
    val getFAQsListResponse: StateFlow<UiState<FAQsListResponse>> = getFAQsList

    val getZoomMeetingToken = MutableStateFlow(UiState<ZoomMeetingTokenResponse>())
    val getZoomMeetingTokenResponse: StateFlow<UiState<ZoomMeetingTokenResponse>> =
        getZoomMeetingToken

    val getToken = MutableStateFlow(UiState<TokenResponse>())
    val getTokenResponse: StateFlow<UiState<TokenResponse>> = getToken

    private val getMeetingList = MutableStateFlow(UiState<ZoomMeetingListResponse>())
    val getMeetingListResponse: StateFlow<UiState<ZoomMeetingListResponse>> = getMeetingList

    private val getMeetingJoin = MutableStateFlow(UiState<ZoomMeetingsJoinResponse>())
    val getMeetingJoinResponse: StateFlow<UiState<ZoomMeetingsJoinResponse>> = getMeetingJoin

    val getChangeRequest = MutableStateFlow(UiState<ChangeRequestResponse>())
    val getChangeRequestResponse: StateFlow<UiState<ChangeRequestResponse>> = getChangeRequest

    val getTrackRequest = MutableStateFlow(UiState<TrackRequestResponse>())
    val getTrackRequestResponse: StateFlow<UiState<TrackRequestResponse>> = getTrackRequest

    val getAccountDelete = MutableStateFlow(UiState<AccountDeleteResponse>())
    val getAccountDeleteResponse: StateFlow<UiState<AccountDeleteResponse>> = getAccountDelete

    val changePassword = MutableStateFlow(UiState<ForgetPasswordResponse>())
    val changePasswordResponse: StateFlow<UiState<ForgetPasswordResponse>> = changePassword

    val checkProfile = MutableStateFlow(UiState<CheckProfileCompletionResponse>())
    val checkProfileResponse: StateFlow<UiState<CheckProfileCompletionResponse>> = checkProfile

    fun isNetworkAvailable(): Boolean {
        return connectivityObserver.getCurrentStatus() == ConnectivityObserver.Status.Available
    }

    init {
        observeNetworkChangesRefresh()
        viewModelScope.launch {
            query.debounce(1000).filter { it.isNotEmpty() }.collectLatest {
                // Your logic
            }
        }
    }

    // observer function for data syncing
    private fun observeNetworkChangesRefresh() {
        viewModelScope.launch {
            var wasPreviouslyOffline = !isNetworkAvailable()

            connectivityObserver.observe().distinctUntilChanged().collect { status ->
                val isNowOnline = status == ConnectivityObserver.Status.Available

                if (wasPreviouslyOffline && isNowOnline) {
                    logger.d("Reconnected - checking which APIs to retry")

                    if (shouldRefreshAllCategory) getAllCategory()
                    if (shouldRefreshRefreshToken) getRefreshToken()
                }

                wasPreviouslyOffline = !isNowOnline
            }
        }
    }

    fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ) = viewModelScope.launch {
        getCertificate.update { UiState(isLoading = true) }
        getUsesCases.getLMSUserCertificate(certificateRequest, strToken).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getCertificate.update {
                    UiState(error = serverMsg)
                }
            } else {
                getCertificate.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getCertificate.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getCertificate.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getCertificate.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }
    fun getSentNotification(
        userId: Int
    ) = viewModelScope.launch {
        getNotification.update { UiState(isLoading = true) }
        getUsesCases.getSentNotification(userId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getNotification.update {
                    UiState(error = serverMsg)
                }
            } else {
                getNotification.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getNotification.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getNotification.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getNotification.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllCategory() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshAllCategory = true
            getCategoryList.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshAllCategory = false // Reset on successful start

        getCategoryList.update { it.copy(isLoading = true, error = "") }

        getUsesCases.getAllCategoryRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getCategoryList.update {
                    UiState(error = serverMsg)
                }
            } else {
                getCategoryList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getCategoryList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getCategoryList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getCategoryList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllSubCategory() = viewModelScope.launch {
        getSubCategoryList.update { UiState(isLoading = true) }
        getUsesCases.getAllSubCategoryRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getSubCategoryList.update {
                    UiState(error = serverMsg)
                }
            } else {
                getSubCategoryList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getSubCategoryList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getSubCategoryList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getSubCategoryList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllSubCategoryByCategoryId(categoryId: Int) = viewModelScope.launch {
        getSubCategoryByCategoryIdList.update { UiState(isLoading = true) }
        getUsesCases.getAllSubCategoryByCategoryIdRepo(categoryId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getSubCategoryByCategoryIdList.update {
                    UiState(error = serverMsg)
                }
            } else {
                getSubCategoryByCategoryIdList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getSubCategoryByCategoryIdList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getSubCategoryByCategoryIdList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getSubCategoryByCategoryIdList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllFAQs(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String,
    ) = viewModelScope.launch {
        getFAQsList.update { UiState(isLoading = true) }
        getUsesCases.getAllFAQsRepo(
            strKeyword, userTypeId, categoryId, subCategoryId, userId, languageId
        ).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getFAQsList.update {
                    UiState(error = serverMsg)
                }
            } else {
                getFAQsList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getFAQsList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getFAQsList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getFAQsList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getRefreshToken() = viewModelScope.launch {
        if (!isNetworkAvailable()) {
            shouldRefreshRefreshToken = true
            getZoomMeetingToken.update {
                UiState(error = noInternetConnection)
            }
            return@launch
        }

        shouldRefreshRefreshToken = false // Reset on successful start

        getZoomMeetingToken.update { it.copy(isLoading = true, error = "") }

        getUsesCases.getRefreshTokenRepo().catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getZoomMeetingToken.update {
                    UiState(error = serverMsg)
                }
            } else {
                getZoomMeetingToken.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getZoomMeetingToken.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getZoomMeetingToken.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getZoomMeetingToken.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getZoomMeetingsActualToken(
        strAuthKey: String,
        strGrantType: String,
        strRefreshToken: String,
    ) = viewModelScope.launch {
        getToken.update { UiState(isLoading = true) }
        getUsesCases.getZoomMeetingsActualTokenRepo(strAuthKey, strGrantType, strRefreshToken)
            .catch { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getToken.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getToken.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            }.collect { result ->
                result.fold(onSuccess = { data ->
                    getToken.update { UiState(success = data) }
                }, onFailure = { exception ->
                    if (exception.message?.contains(serverError) == true) {
                        getToken.update {
                            UiState(error = serverMsg)
                        }
                    } else {
                        getToken.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                })
            }
    }

    fun getProfileChangeRequest(
        requestChange: ProfileNameChangeRequest,
        strToken: String,
        docPic: ByteArray? = null,
        fileName: String? = null,
    ) = viewModelScope.launch {
        getChangeRequest.update { UiState(isLoading = true) }
        getUsesCases.getChangeRequestCases(requestChange, strToken, docPic, fileName)
            .catch { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getChangeRequest.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getChangeRequest.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            }.collect { result ->
                result.fold(onSuccess = { data ->
                    getChangeRequest.update { UiState(success = data) }
                }, onFailure = { exception ->
                    if (exception.message?.contains(serverError) == true) {
                        getChangeRequest.update {
                            UiState(error = serverMsg)
                        }
                    } else {
                        getChangeRequest.update {
                            UiState(error = exception.message ?: somethingWentWrong)
                        }
                    }
                })
            }
    }

    fun getTrackChangeRequest(
        strToken: String,
        requestTypeId: String,
    ) = viewModelScope.launch {
        getTrackRequest.update { UiState(isLoading = true) }
        getUsesCases.getTrackRequestCases(strToken, requestTypeId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getTrackRequest.update {
                    UiState(error = serverMsg)
                }
            } else {
                getTrackRequest.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getTrackRequest.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getTrackRequest.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getTrackRequest.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getAllZoomMeetings(
        tokenKey: String,
    ) = viewModelScope.launch {
        getMeetingList.update { UiState(isLoading = true) }
        getUsesCases.getAllZoomMeetingsRepo(tokenKey).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getMeetingList.update {
                    UiState(error = serverMsg)
                }
            } else {
                getMeetingList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getMeetingList.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getMeetingList.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getMeetingList.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun getJoinZoomMeetings(
        tokenKey: String,
        meetingId: Long,
    ) = viewModelScope.launch {
        getMeetingJoin.update { UiState(isLoading = true) }
        getUsesCases.getJoinZoomMeetingsRepo(tokenKey, meetingId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getMeetingJoin.update {
                    UiState(error = serverMsg)
                }
            } else {
                getMeetingJoin.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getMeetingJoin.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getMeetingJoin.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getMeetingJoin.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun changePassword(
        passwordRequest: ChangePasswordRequest,
        strToken: String,
    ) = viewModelScope.launch {
        changePassword.update { UiState(isLoading = true) }
        getUsesCases.changePassword(passwordRequest, strToken).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                changePassword.update {
                    UiState(error = serverMsg)
                }
            } else {
                changePassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                changePassword.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    changePassword.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    changePassword.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun deactivateUser(
        tokenKey: String,
        userId: String,
    ) = viewModelScope.launch {
        getAccountDelete.update { UiState(isLoading = true) }
        getUsesCases.deactivateUserRepo(tokenKey, userId).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                getAccountDelete.update {
                    UiState(error = serverMsg)
                }
            } else {
                getAccountDelete.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getAccountDelete.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    getAccountDelete.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    getAccountDelete.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }

    fun checkProfileCompletion(
        tokenKey: String,
    ) = viewModelScope.launch {
        checkProfile.update { UiState(isLoading = true) }
        getUsesCases.checkProfileCompletionRepo(tokenKey).catch { exception ->
            if (exception.message?.contains(serverError) == true) {
                checkProfile.update {
                    UiState(error = serverMsg)
                }
            } else {
                checkProfile.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                checkProfile.update { UiState(success = data) }
            }, onFailure = { exception ->
                if (exception.message?.contains(serverError) == true) {
                    checkProfile.update {
                        UiState(error = serverMsg)
                    }
                } else {
                    checkProfile.update {
                        UiState(error = exception.message ?: somethingWentWrong)
                    }
                }
            })
        }
    }
}