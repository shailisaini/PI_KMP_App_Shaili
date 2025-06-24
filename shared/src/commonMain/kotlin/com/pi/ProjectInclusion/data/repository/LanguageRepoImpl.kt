package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.LanguageRepository

class LanguageRepoImpl(private val apiService: ApiService) : LanguageRepository{
    override suspend fun getLanguage(page: String, limit: String): GetLanguageListResponse {
        return apiService.getLanguages(page, limit)
    }
}

