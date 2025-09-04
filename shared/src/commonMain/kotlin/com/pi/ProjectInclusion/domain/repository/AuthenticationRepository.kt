package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForceUpdateResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ProfessionListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.QualificationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ReasonListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SpecializationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse

interface AuthenticationRepository {
    suspend fun getLanguage(): GetLanguageListResponse
    suspend fun getUserType(): GetUserTypeResponse
    suspend fun getOTPOnCall(mobNo: String): SendOTPResponse
    suspend fun getUserLoginPasswordRepo(loginRequest: LoginRequest): LoginApiResponse
    suspend fun getOTPOnWhatsapp(mobNo: String): SendOTPResponse
    suspend fun getValidate(userName: String, userTypeId: String): ValidateUserResponse
    suspend fun getVerifyOtpRepo(userName: String, userTypeId: String): VerifyOtpResponse
    suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest): LoginApiResponse
    suspend fun getUserProfileRepo(userName: String): ViewProfileResponse
    suspend fun forgetPasswordRepo(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse

    suspend fun createRegisterPasswordRepo(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ): CreateRegisterPasswordResponse

    suspend fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null,
    ): CreateFirstStepProfileResponse

    suspend fun getAllStateListRepo(): List<StateListResponse>

    suspend fun getAllDistrictByStateIdRepo(stateId: Int): List<DistrictListResponse>

    suspend fun getAllBlockByDistrictIdRepo(stateId: Int): List<BlockListResponse>

    suspend fun getAllSchoolsByBlockIdRepo(blockId: Int): SchoolListResponse

    suspend fun getAllDetailsByUdiseIdRepo(udiseCode: String): SchoolByUdiseCodeResponse

    suspend fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ): CreateFirstStepProfileResponse

    suspend fun getAllProfessionRepo(): List<ProfessionListResponse>

    suspend fun getAllQualificationRepo(profession: Int): List<QualificationListResponse>

    suspend fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int,
    ): List<SpecializationListResponse>

    suspend fun getAllReasonRepo(): ReasonListResponse

    suspend fun getForceUpdateAppRepo(
        deviceOsVersion: Double, latestAppVersion: Double,
    ): ForceUpdateResponse
}