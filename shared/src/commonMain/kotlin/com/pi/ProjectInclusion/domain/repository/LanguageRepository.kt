package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse

interface LanguageRepository {
    suspend fun getLanguage(page: String, limit: String) :GetLanguageListResponse
}