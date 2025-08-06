package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.SendOTPResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.LanguageRepository

class LanguageRepoImpl(private val apiService: ApiService) : LanguageRepository{
    override suspend fun getLanguage(): GetLanguageListResponse {
        return apiService.getLanguages()
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        return apiService.getUserType()
    }

    override suspend fun getOTPOnCall(mobNo : String): SendOTPResponse {
        return apiService.getOTPOnCall(mobNo)
    }

    override suspend fun getOTPOnWhatsapp(mobNo : String): SendOTPResponse {
        return apiService.getOTPOnWhatsapp(mobNo)
    }
}

