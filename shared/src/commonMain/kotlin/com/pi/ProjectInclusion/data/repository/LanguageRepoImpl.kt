package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.LanguageRepository

class LanguageRepoImpl(private val apiService: ApiService) : LanguageRepository{
    override suspend fun getLanguage(): GetLanguageListResponse {
        return apiService.getLanguages()
    }

    override suspend fun getUserType(): GetUserTypeResponse {
        return apiService.getUserType()
    }
}

