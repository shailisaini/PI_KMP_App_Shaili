package com.pi.ProjectInclusion.data.repository

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
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
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

    override suspend fun getUserProfileRepo(userName: String): LoginApiResponse {
        return apiService.getViewUserProfile(userName)
    }
}

