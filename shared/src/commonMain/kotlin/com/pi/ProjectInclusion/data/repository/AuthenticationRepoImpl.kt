package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
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

   override suspend fun forgetPassword(
       passwordRequest: ForgetPasswordRequest,
       strToken: String,
    ): ForgetPasswordResponse {
        return apiService.forgetPassword(passwordRequest, strToken)
    }
}

