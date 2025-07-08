package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse

interface LanguageRepository {
    suspend fun getLanguage(page: String, limit: String) :GetLanguageListResponse
    suspend fun getUserType() :GetUserTypeResponse
}