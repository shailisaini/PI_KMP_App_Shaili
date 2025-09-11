package dashboard

import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.AccountDeleteResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse.CertificateResponse
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
import com.pi.ProjectInclusion.data.model.profileModel.response.TrackRequestResponse.RequestResponse
import com.pi.ProjectInclusion.domain.repository.DashboardRepository

class FakeDashboardRepository : DashboardRepository {
    var shouldSucceed = true
    override suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String
    ): CertificateListResponse {
        return if (shouldSucceed) {
            CertificateListResponse(status = 0, message = "LMS request successful",
                response = arrayListOf(CertificateResponse(0,0,0,"",0,"","","","",0,"","","","",0,"","","","","","","","","","",0,"",0,"",0,"","")),
                errorCode ="")
        } else {
            throw Exception("Request Not sent!")
        }
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
        return if (shouldSucceed) {
            arrayListOf(CategoryListResponse(status = 0, name = "Faq Category", id =0, priority = 0))
        } else {
            throw Exception("Request Not found!")
        }
    }

    override suspend fun getAllSubCategoryRepo(): List<SubCategoryListResponse> {
        return if (shouldSucceed) {
            arrayListOf(SubCategoryListResponse(status = 0, name = "Faq Sub Category", id =0, priority = 0))
        } else {
            throw Exception("Sub-Category Not found!")
        }
    }

    override suspend fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): SubCategoryByCategoryIdResponse {
        return if (shouldSucceed) {
            SubCategoryByCategoryIdResponse(status = 0, errorCode = "200", message = "All Sub-category by Category")
        } else {
            throw Exception("No Sub-category by Category")
        }
    }

    override suspend fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String
    ): FAQsListResponse {
        return if (shouldSucceed) {
            FAQsListResponse(status = 0, errorCode = "200", message = "All FAQ")
        } else {
            throw Exception("All FAQ")
        }
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
        return if (shouldSucceed) {
            ForgetPasswordResponse(status = true, message = "Password change Successfully!")
        } else {
            throw Exception("Password Error!")
        }
    }

    override suspend fun deactivateUserRepo(
        tokenKey: String,
        userId: String
    ): AccountDeleteResponse {
        return if (shouldSucceed) {
            AccountDeleteResponse(status = true, message = "Deactivate user!")
        } else {
            throw Exception("Error to deactivate user")
        }
    }

    override suspend fun checkProfileCompletionRepo(tokenKey: String): CheckProfileCompletionResponse {
        return if (shouldSucceed) {
            CheckProfileCompletionResponse(status = true, message = "Check Profile Completion")
        } else {
            throw Exception("Error!")
        }
    }

}