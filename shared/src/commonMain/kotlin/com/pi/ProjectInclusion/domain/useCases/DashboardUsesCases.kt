package com.pi.ProjectInclusion.domain.useCases

import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.AccountDeleteResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
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
import com.pi.ProjectInclusion.domain.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashboardUsesCases(private val repository: DashboardRepository) {

    var unableToConnectServer: String = "Unable to connect to server"

    fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): Flow<Result<CertificateListResponse>> = flow {
        try {
            val response = repository.getLMSUserCertificateRepo(certificateRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in certificate $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllCategoryRepo(): Flow<Result<List<CategoryListResponse>>> = flow {
        try {
            val response = repository.getAllCategoryRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getAllCategoryRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllSubCategoryRepo(): Flow<Result<List<SubCategoryListResponse>>> = flow {
        try {
            val response = repository.getAllSubCategoryRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getAllSubCategoryRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): Flow<Result<SubCategoryByCategoryIdResponse>> =
        flow {
            try {
                val response = repository.getAllSubCategoryByCategoryIdRepo(categoryId)
                emit(Result.success(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: unableToConnectServer
                logger.d("Exception in getAllSubCategoryRepo() $errorMessage")
                emit(Result.failure(Exception(errorMessage)))
            }
        }.flowOn(Dispatchers.IO)

    fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String,
    ): Flow<Result<FAQsListResponse>> = flow {
        try {
            val response = repository.getAllFAQsRepo(
                strKeyword, userTypeId, categoryId, subCategoryId, userId, languageId
            )
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getAllFAQsRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getRefreshTokenRepo(): Flow<Result<ZoomMeetingTokenResponse>> = flow {
        try {
            val response = repository.getRefreshTokenRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getRefreshTokenRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getZoomMeetingsActualTokenRepo(
        strAuthKey: String,
        strGrantType: String,
        strRefreshToken: String,
    ): Flow<Result<TokenResponse>> = flow {
        try {
            val response =
                repository.getZoomMeetingsActualTokenRepo(strAuthKey, strGrantType, strRefreshToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getZoomMeetingsActualTokenRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getChangeRequestCases(
        changeRequest: ProfileNameChangeRequest,
        strToken: String,
        docPic: ByteArray? = null,
        fileName: String? = null,
    ): Flow<Result<ChangeRequestResponse>> = flow {
        try {
            val response =
                repository.getChangeRequestRepo(changeRequest, strToken, docPic, fileName)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in certificate $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getTrackRequestCases(
        strToken: String,
        requestTypeId: String,
    ): Flow<Result<TrackRequestResponse>> = flow {
        try {
            val response = repository.TrackRequestResponseRepo(strToken, requestTypeId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in certificate $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllZoomMeetingsRepo(
        tokenKey: String,
    ): Flow<Result<ZoomMeetingListResponse>> = flow {
        try {
            val response =
                repository.getAllZoomMeetingsRepo(tokenKey)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getAllZoomMeetingsRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getJoinZoomMeetingsRepo(
        tokenKey: String,
        meetingId: Long,
    ): Flow<Result<ZoomMeetingsJoinResponse>> = flow {
        try {
            val response =
                repository.getJoinZoomMeetingsRepo(tokenKey, meetingId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in getJoinZoomMeetingsRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun deactivateUserRepo(
        tokenKey: String,
        userId: String,
    ): Flow<Result<AccountDeleteResponse>> = flow {
        try {
            val response =
                repository.deactivateUserRepo(tokenKey, userId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in deactivateUser() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun changePassword(
        passwordRequest: ChangePasswordRequest,
        strToken: String,
    ): Flow<Result<ForgetPasswordResponse>> = flow {
        try {
            val response = repository.changePasswordRepo(passwordRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in changePassword() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun checkProfileCompletionRepo(strToken: String): Flow<Result<CheckProfileCompletionResponse>> =
        flow {
            try {
                val response = repository.checkProfileCompletionRepo(strToken)
                emit(Result.success(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: unableToConnectServer
                logger.d("Exception in checkProfileCompletionRepo() $errorMessage")
                emit(Result.failure(Exception(errorMessage)))
            }
        }.flowOn(Dispatchers.IO)
}