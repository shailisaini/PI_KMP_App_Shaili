package authentication

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
import com.pi.ProjectInclusion.domain.repository.AuthenticationRepository
import kotlinx.serialization.SerialName

class FakeAuthenticationRepository : AuthenticationRepository {

    var shouldSucceed = true

    override suspend fun getLanguage(): GetLanguageListResponse {
        return if (shouldSucceed) {
            GetLanguageListResponse(status = true, message = "Login Success")
        } else {
            throw Exception("data not found")
        }
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        return if (shouldSucceed) {
            GetUserTypeResponse(status = true, message = "Login Success")
        } else {
            throw Exception("data not found")
        }
    }

    override suspend fun getOTPOnCall(mobNo: String): SendOTPResponse {
        return if (shouldSucceed) {
            SendOTPResponse(status = true, message = "Login Success")
        } else {
            throw Exception("Invalid credentials")
        }
    }

    override suspend fun getUserLoginPasswordRepo(loginRequest: LoginRequest): LoginApiResponse {
        return if (shouldSucceed) {
            LoginApiResponse(status = true, message = "Login success")
        } else {
            LoginApiResponse(status = false, message = "Invalid credentials") // non-empty message
        }
    }

    override suspend fun getOTPOnWhatsapp(mobNo: String): SendOTPResponse {
        return if (shouldSucceed) {
            SendOTPResponse(status = true, message = "Login success")
        } else {
            throw Exception("Invalid User")
        }
    }

    override suspend fun getValidate(
        userName: String,
        userTypeId: String,
    ): ValidateUserResponse {
        return if (shouldSucceed) {
            ValidateUserResponse(status = true, message = "Login success")
        } else {
            throw Exception("Invalid User")
        }
    }

    override suspend fun getVerifyOtpRepo(
        userName: String,
        userTypeId: String,
    ): VerifyOtpResponse {
        return if (shouldSucceed) {
            VerifyOtpResponse(status = true, message = "Login success")
        } else {
            throw Exception("Invalid OTP")
        }
    }

    override suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest): LoginApiResponse {
        return if (shouldSucceed) {
            LoginApiResponse(status = true, message = "Login success")
        } else {
            throw Exception("Invalid OTP")
        }
    }

    override suspend fun getUserProfileRepo(
        token: String,
        userName: String,
    ): ViewProfileResponse {
        return if (shouldSucceed) {
            ViewProfileResponse(status = true, message = "User Success", error = "")
        } else {
            throw Exception("Invalid User")
        }
    }

    override suspend fun forgetPasswordRepo(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse {
        return if (shouldSucceed) {
            ForgetPasswordResponse(status = true, message = "User Success", error = "")
        } else {
            throw Exception("Invalid Password")
        }
    }

    override suspend fun createRegisterPasswordRepo(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ): CreateRegisterPasswordResponse {
        return if (shouldSucceed) {
            CreateRegisterPasswordResponse(status = true, message = "User Success", error = "")
        } else {
            throw Exception("Invalid User/Password")
        }
    }

    override suspend fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray?,
        fileName: String?,
    ): CreateFirstStepProfileResponse {
        return if (shouldSucceed) {
            CreateFirstStepProfileResponse(status = true, message = "User Success", error = "")
        } else {
            throw Exception("Invalid Fields")
        }
    }

    override suspend fun getAllStateListRepo(): List<StateListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                StateListResponse(
                    "name", "code", 0, "countryName", 0, "createdDate", "updatedDate", 0, 0, 0, 0
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllDistrictByStateIdRepo(stateId: Int): List<DistrictListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                DistrictListResponse(
                    0, "name", "code", 0, "stateName", 0, "divisionName", 0, 0
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllBlockByDistrictIdRepo(stateId: Int): List<BlockListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                BlockListResponse(
                    0,
                    "createdDate",
                    "updatedDate",
                    0,
                    "updatedBy",
                    0,
                    0,
                    "name",
                    "code",
                    0,
                    "districtName",
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllSchoolsByBlockIdRepo(blockId: Int): SchoolListResponse {
        return if (shouldSucceed) {
            SchoolListResponse(status = 0, message = "User Success")
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllDetailsByUdiseIdRepo(udiseCode: String): SchoolByUdiseCodeResponse {
        return if (shouldSucceed) {
            SchoolByUdiseCodeResponse(status = 0, message = "User Success")
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ): CreateFirstStepProfileResponse {
        return if (shouldSucceed) {
            CreateFirstStepProfileResponse(status = true, message = "User Success")
        } else {
            throw Exception("Invalid Fields")
        }
    }

    override suspend fun getAllProfessionRepo(): List<ProfessionListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                ProfessionListResponse(
                    "name", 0, "createdDate", "updatedDate", 0, "updatedBy", 0, 0
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllQualificationRepo(profession: Int): List<QualificationListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                QualificationListResponse(
                    0, "name", 0, 0, 0, 0, "createdDate", "updatedBy", "updatedDate"
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int,
    ): List<SpecializationListResponse> {
        return if (shouldSucceed) {
            arrayListOf(
                SpecializationListResponse(
                    0, "name", 0, 0, 0, 0, 0, "createdDate", "updatedBy", "updatedDate"
                )
            )
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getAllReasonRepo(): ReasonListResponse {
        return if (shouldSucceed) {
            ReasonListResponse(status = true, message = "User Success")
        } else {
            throw Exception("Data not found")
        }
    }

    override suspend fun getForceUpdateAppRepo(
        deviceOsVersion: Double,
        latestAppVersion: Double,
    ): ForceUpdateResponse {
        return if (shouldSucceed) {
            ForceUpdateResponse(status = 1, message = "User Success")
        } else {
            throw Exception("Data not found")
        }
    }
}