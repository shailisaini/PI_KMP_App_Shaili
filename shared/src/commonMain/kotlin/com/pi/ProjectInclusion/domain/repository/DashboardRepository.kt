package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
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

interface DashboardRepository {

    suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): CertificateListResponse

    suspend fun getChangeRequestRepo(
        certificateRequest: ProfileNameChangeRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null,
    ): ChangeRequestResponse

    suspend fun TrackRequestResponseRepo(
        strToken: String,
       requestTypeId: String,
    ): TrackRequestResponse

    suspend fun getAllCategoryRepo(): List<CategoryListResponse>

    suspend fun getAllSubCategoryRepo(): List<SubCategoryListResponse>

    suspend fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): SubCategoryByCategoryIdResponse

    suspend fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String,
    ): FAQsListResponse

    suspend fun getRefreshTokenRepo(): ZoomMeetingTokenResponse

    suspend fun getZoomMeetingsActualTokenRepo(
        strAuthKey: String,
        strGrantType: String,
        strRefreshToken: String,
    ): TokenResponse

    suspend fun getAllZoomMeetingsRepo(
        tokenKey: String,
    ): ZoomMeetingListResponse

    suspend fun getJoinZoomMeetingsRepo(
        tokenKey: String,
        meetingId: Long,
    ): ZoomMeetingsJoinResponse

    suspend fun changePasswordRepo(
        passwordRequest: ChangePasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse
}