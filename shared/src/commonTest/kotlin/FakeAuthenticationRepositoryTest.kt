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

class FakeAuthenticationRepository : AuthenticationRepository {
    var shouldSucceed = true

    override suspend fun getLanguage(): GetLanguageListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun getValidate(
        userName: String,
        userTypeId: String
    ): ValidateUserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getVerifyOtpRepo(
        userName: String,
        userTypeId: String
    ): VerifyOtpResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest): LoginApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfileRepo(userName: String): ViewProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun forgetPasswordRepo(
        passwordRequest: ForgetPasswordRequest,
        strToken: String
    ): ForgetPasswordResponse {
        TODO("Not yet implemented")
    }

    override suspend fun createRegisterPasswordRepo(
        passwordRequest: CreatePasswordRequest,
        strToken: String
    ): CreateRegisterPasswordResponse {
        TODO("Not yet implemented")
    }

    override suspend fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray?,
        fileName: String?
    ): CreateFirstStepProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStateListRepo(): List<StateListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDistrictByStateIdRepo(stateId: Int): List<DistrictListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBlockByDistrictIdRepo(stateId: Int): List<BlockListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSchoolsByBlockIdRepo(blockId: Int): SchoolListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDetailsByUdiseIdRepo(udiseCode: String): SchoolByUdiseCodeResponse {
        TODO("Not yet implemented")
    }

    override suspend fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String
    ): CreateFirstStepProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProfessionRepo(): List<ProfessionListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllQualificationRepo(profession: Int): List<QualificationListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int
    ): List<SpecializationListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllReasonRepo(): ReasonListResponse {
        TODO("Not yet implemented")
    }
}