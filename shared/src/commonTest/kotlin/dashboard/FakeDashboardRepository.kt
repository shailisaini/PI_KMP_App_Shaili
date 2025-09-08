package dashboard

import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.AccountDeleteResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
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
import com.pi.ProjectInclusion.data.model.profileModel.response.TrackRequestResponse.RequestResponse
import com.pi.ProjectInclusion.domain.repository.DashboardRepository

class FakeDashboardRepository : DashboardRepository {
    var shouldSucceed = true
    override suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String
    ): CertificateListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getChangeRequestRepo(
        certificateRequest: ProfileNameChangeRequest,
        strToken: String,
        profilePic: ByteArray?,
        fileName: String?
    ): ChangeRequestResponse {

        return if (shouldSucceed) {
            ChangeRequestResponse(status = true, message = "Change request successful", response = "", error ="")
        } else {
            throw Exception("Request Not sent!")
        }
    }

    override suspend fun TrackRequestResponseRepo(
        strToken: String,
        requestTypeId: String
    ): TrackRequestResponse {
        return if (shouldSucceed) {
            TrackRequestResponse(status = true, message = "Track request successful",
                response = RequestResponse(true,"Already Requested"), error ="")
        } else {
            throw Exception("Request Not found!")
        }
    }

    override suspend fun getAllCategoryRepo(): List<CategoryListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSubCategoryRepo(): List<SubCategoryListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): SubCategoryByCategoryIdResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String
    ): FAQsListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshTokenRepo(): ZoomMeetingTokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getZoomMeetingsActualTokenRepo(
        strAuthKey: String,
        strGrantType: String,
        strRefreshToken: String
    ): TokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllZoomMeetingsRepo(tokenKey: String): ZoomMeetingListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getJoinZoomMeetingsRepo(
        tokenKey: String,
        meetingId: Long
    ): ZoomMeetingsJoinResponse {
        TODO("Not yet implemented")
    }

    override suspend fun changePasswordRepo(
        passwordRequest: ChangePasswordRequest,
        strToken: String
    ): ForgetPasswordResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deactivateUserRepo(
        tokenKey: String,
        userId: String
    ): AccountDeleteResponse {
        TODO("Not yet implemented")
    }

}