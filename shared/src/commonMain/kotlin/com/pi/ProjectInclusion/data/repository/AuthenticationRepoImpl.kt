package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.SendOTPResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.ValidateUserResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.AuthenticationRepository

class AuthenticationRepoImpl(private val apiService: ApiService) : AuthenticationRepository{
    override suspend fun getLanguage(): GetLanguageListResponse {
        return apiService.getLanguages()
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        return apiService.getUserType()
    }

    override suspend fun getValidate(userName : String, userTypeId : String): ValidateUserResponse {
        return apiService.getValidateUser(userName, userTypeId)
    }

    override suspend fun getOTPOnCall(mobNo : String): SendOTPResponse {
        return apiService.getOTPOnCall(mobNo)
    }

    override suspend fun getOTPOnWhatsapp(mobNo : String): SendOTPResponse {
        return apiService.getOTPOnWhatsapp(mobNo)
    }
}

