package com.pi.ProjectInclusion.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.AccountDeleteResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CheckProfileCompletionResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.FAQsListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
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

    private val getCategoryList = MutableStateFlow(UiState<List<CategoryListResponse>>())
    val getCategoryListResponse: StateFlow<UiState<List<CategoryListResponse>>> = getCategoryList

    private val getSubCategoryList = MutableStateFlow(UiState<List<SubCategoryListResponse>>())
    val getSubCategoryListResponse: StateFlow<UiState<List<SubCategoryListResponse>>> =
        getSubCategoryList

    private val getSubCategoryByCategoryIdList =
        MutableStateFlow(UiState<SubCategoryByCategoryIdResponse>())
    val getSubCategoryByCategoryIdListResponse: StateFlow<UiState<SubCategoryByCategoryIdResponse>> =
        getSubCategoryByCategoryIdList

    private val getFAQsList = MutableStateFlow(UiState<FAQsListResponse>())
    val getFAQsListResponse: StateFlow<UiState<FAQsListResponse>> = getFAQsList

    private val getZoomMeetingToken = MutableStateFlow(UiState<ZoomMeetingTokenResponse>())
    val getZoomMeetingTokenResponse: StateFlow<UiState<ZoomMeetingTokenResponse>> =
        getZoomMeetingToken

    private val getToken = MutableStateFlow(UiState<TokenResponse>())
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

    private val changePassword = MutableStateFlow(UiState<ForgetPasswordResponse>())
    val changePasswordResponse: StateFlow<UiState<ForgetPasswordResponse>> = changePassword

    private val checkProfile = MutableStateFlow(UiState<CheckProfileCompletionResponse>())
    val checkProfileResponse: StateFlow<UiState<CheckProfileCompletionResponse>> = checkProfile

    fun isNetworkAvailable(): Boolean {
        return connectivityObserver.getCurrentStatus() == ConnectivityObserver.Status.Available
    }

    init {
        viewModelScope.launch {
            query.debounce(1000).filter { it.isNotEmpty() }.collectLatest { }
        }
    }

    fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ) = viewModelScope.launch {
        getCertificate.update { UiState(isLoading = true) }
        getUsesCases.getLMSUserCertificate(certificateRequest, strToken).catch { exception ->
            getCertificate.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getCertificate.update { UiState(success = data) }
            }, onFailure = { exception ->
                getCertificate.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }

    fun getAllCategory() = viewModelScope.launch {
        getCategoryList.update { UiState(isLoading = true) }
        getUsesCases.getAllCategoryRepo().catch { exception ->
            getCategoryList.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getCategoryList.update { UiState(success = data) }
            }, onFailure = { exception ->
                getCategoryList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }

    fun getAllSubCategory() = viewModelScope.launch {
        getSubCategoryList.update { UiState(isLoading = true) }
        getUsesCases.getAllSubCategoryRepo().catch { exception ->
            getSubCategoryList.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getSubCategoryList.update { UiState(success = data) }
            }, onFailure = { exception ->
                getSubCategoryList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }

    fun getAllSubCategoryByCategoryId(categoryId: Int) = viewModelScope.launch {
        getSubCategoryByCategoryIdList.update { UiState(isLoading = true) }
        getUsesCases.getAllSubCategoryByCategoryIdRepo(categoryId).catch { exception ->
            getSubCategoryByCategoryIdList.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getSubCategoryByCategoryIdList.update { UiState(success = data) }
            }, onFailure = { exception ->
                getSubCategoryByCategoryIdList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
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
            getFAQsList.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getFAQsList.update { UiState(success = data) }
            }, onFailure = { exception ->
                getFAQsList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }


    fun getRefreshToken() = viewModelScope.launch {
        getZoomMeetingToken.update { UiState(isLoading = true) }
        getUsesCases.getRefreshTokenRepo().catch { exception ->
            getZoomMeetingToken.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getZoomMeetingToken.update { UiState(success = data) }
            }, onFailure = { exception ->
                getZoomMeetingToken.update {
                    UiState(error = exception.message ?: somethingWentWrong)
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
                getToken.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }.collect { result ->
                result.fold(onSuccess = { data ->
                    getToken.update { UiState(success = data) }
                }, onFailure = { exception ->
                    getToken.update {
                        UiState(error = exception.message ?: somethingWentWrong)
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
                getChangeRequest.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            }.collect { result ->
                result.fold(onSuccess = { data ->
                    getChangeRequest.update { UiState(success = data) }
                }, onFailure = { exception ->
                    getChangeRequest.update {
                        UiState(error = exception.message ?: somethingWentWrong)
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
            getTrackRequest.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getTrackRequest.update { UiState(success = data) }
            }, onFailure = { exception ->
                getTrackRequest.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }

    fun getAllZoomMeetings(
        tokenKey: String,
    ) = viewModelScope.launch {
        getMeetingList.update { UiState(isLoading = true) }
        getUsesCases.getAllZoomMeetingsRepo(tokenKey).catch { exception ->
            getMeetingList.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getMeetingList.update { UiState(success = data) }
            }, onFailure = { exception ->
                getMeetingList.update {
                    UiState(error = exception.message ?: somethingWentWrong)
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
            getMeetingJoin.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getMeetingJoin.update { UiState(success = data) }
            }, onFailure = { exception ->
                getMeetingJoin.update {
                    UiState(error = exception.message ?: somethingWentWrong)
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
            changePassword.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                changePassword.update { UiState(success = data) }
            }, onFailure = { exception ->
                changePassword.update {
                    UiState(error = exception.message ?: somethingWentWrong)
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
            getAccountDelete.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                getAccountDelete.update { UiState(success = data) }
            }, onFailure = { exception ->
                getAccountDelete.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }

    fun checkProfileCompletion(
        tokenKey: String,
    ) = viewModelScope.launch {
        checkProfile.update { UiState(isLoading = true) }
        getUsesCases.checkProfileCompletionRepo(tokenKey).catch { exception ->
            checkProfile.update {
                UiState(error = exception.message ?: somethingWentWrong)
            }
        }.collect { result ->
            result.fold(onSuccess = { data ->
                checkProfile.update { UiState(success = data) }
            }, onFailure = { exception ->
                checkProfile.update {
                    UiState(error = exception.message ?: somethingWentWrong)
                }
            })
        }
    }
}