package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
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
import com.pi.ProjectInclusion.data.model.profileModel.ViewProfileResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.AuthenticationRepository

class AuthenticationRepoImpl(private val apiService: ApiService) : AuthenticationRepository {
    override suspend fun getLanguage(): GetLanguageListResponse {
        return apiService.getLanguages()
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        return apiService.getUserType()
    }

    override suspend fun getValidate(userName: String, userTypeId: String): ValidateUserResponse {
        return apiService.getValidateUser(userName, userTypeId)
    }

    override suspend fun getUserLoginPasswordRepo(loginRequest: LoginRequest): LoginApiResponse {
        return apiService.getUserLoginWithPassword(loginRequest)
    }

    override suspend fun getOTPOnCall(mobNo: String): SendOTPResponse {
        return apiService.getOTPOnCall(mobNo)
    }

    override suspend fun getOTPOnWhatsapp(mobNo: String): SendOTPResponse {
        return apiService.getOTPOnWhatsapp(mobNo)
    }

    override suspend fun forgetPasswordRepo(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse {
        return apiService.forgetPassword(passwordRequest, strToken)
    }

    override suspend fun createRegisterPasswordRepo(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ): CreateRegisterPasswordResponse {
        return apiService.createRegisterPassword(passwordRequest, strToken)
    }

    override suspend fun getVerifyOtpRepo(mobNo: String, otpValue: String): VerifyOtpResponse {
        return apiService.getVerifyOTP(mobNo, otpValue)
    }

    override suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest): LoginApiResponse {
        return apiService.getLoginWithOTP(request)
    }

    override suspend fun getUserProfileRepo(userName: String): ViewProfileResponse {
        return apiService.getViewUserProfile(userName)
    }

    override suspend fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray?,
        fileName: String?,
    ): CreateFirstStepProfileResponse {
        return apiService.createFirstStepProfile(
            firstStepProfileRequest,
            strToken,
            profilePic,
            fileName
        )
    }

    override suspend fun getAllStateListRepo(): List<StateListResponse> {
        return apiService.getAllStateList()
    }

    override suspend fun getAllDistrictByStateIdRepo(stateId: Int): List<DistrictListResponse> {
        return apiService.getAllDistrictByStateId(stateId)
    }

    override suspend fun getAllBlockByDistrictIdRepo(districtId: Int): List<BlockListResponse> {
        return apiService.getAllBlockByDistrictId(districtId)
    }

    override suspend fun getAllSchoolsByBlockIdRepo(blockId: Int): SchoolListResponse {
        return apiService.getAllSchoolsByBlockId(blockId)
    }

    override suspend fun getAllDetailsByUdiseIdRepo(udiseCode: String): SchoolByUdiseCodeResponse {
        return apiService.getAllDetailsByUdiseId(udiseCode)
    }

    override suspend fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ): CreateFirstStepProfileResponse {
        return apiService.createProfessionalProfile(professionalProfileRequest, strToken)
    }

    override suspend fun getAllProfessionRepo(): List<ProfessionListResponse> {
        return apiService.getAllProfession()
    }

    override suspend fun getAllQualificationRepo(profession: Int): List<QualificationListResponse> {
        return apiService.getAllQualification(profession)
    }

    override suspend fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int,
    ): List<SpecializationListResponse> {
        return apiService.getAllSpecialization(profession, qualification)
    }

    override suspend fun getAllReasonRepo(): ReasonListResponse {
        return apiService.getAllReason()
    }

    override suspend fun getForceUpdateAppRepo(
        deviceOsVersion: Double,
        latestAppVersion: Double,
    ): ForceUpdateResponse {
        return apiService.getForceUpdateApp(deviceOsVersion, latestAppVersion)
    }
}

