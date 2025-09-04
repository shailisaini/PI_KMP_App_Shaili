package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.FAQsListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryByCategoryIdResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.TokenResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingTokenResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingsJoinResponse
import com.pi.ProjectInclusion.data.model.profileModel.ProfileNameChangeRequest
import com.pi.ProjectInclusion.data.model.profileModel.response.ChangeRequestResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.TrackRequestResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.DashboardRepository

class DashboardRepoImpl(private val apiService: ApiService) : DashboardRepository {

    override suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): CertificateListResponse {
        return apiService.getLMSUserCertificate(certificateRequest, strToken)
    }

    override suspend fun getChangeRequestRepo(
        changeRequest: ProfileNameChangeRequest,
        strToken: String,
        profilePic: ByteArray?,
        fileName: String?
    ): ChangeRequestResponse {
        return apiService.changeRequestApi(changeRequest, strToken,profilePic, fileName)
    }

    override suspend fun TrackRequestResponseRepo(
        strToken: String,
        requstTypeId: String
    ): TrackRequestResponse {
        return apiService.trackChangeRequestApi(strToken,requstTypeId)
    }

    override suspend fun getAllCategoryRepo(): List<CategoryListResponse> {
        return apiService.getAllCategory()
    }

    override suspend fun getAllSubCategoryRepo(): List<SubCategoryListResponse> {
        return apiService.getAllSubCategory()
    }

    override suspend fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): SubCategoryByCategoryIdResponse {
        return apiService.getAllSubCategoryByCategoryId(categoryId)
    }

    override suspend fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String,
    ): FAQsListResponse {
        return apiService.getAllFAQs(
            strKeyword, userTypeId, categoryId, subCategoryId, userId, languageId
        )
    }

    override suspend fun getRefreshTokenRepo(): ZoomMeetingTokenResponse {
        return apiService.getRefreshToken()
    }

    override suspend fun getZoomMeetingsActualTokenRepo(
        strAuthKey: String,
        strGrantType: String,
        strRefreshToken: String,
    ): TokenResponse {
        return apiService.getZoomMeetingsActualToken(strAuthKey, strGrantType, strRefreshToken)
    }

    override suspend fun getAllZoomMeetingsRepo(tokenKey: String): ZoomMeetingListResponse {
        return apiService.getAllZoomMeetings(tokenKey)
    }

    override suspend fun getJoinZoomMeetingsRepo(
        tokenKey: String,
        meetingId: Long,
    ): ZoomMeetingsJoinResponse {
        return apiService.getJoinZoomMeetings(tokenKey, meetingId)
    }
}