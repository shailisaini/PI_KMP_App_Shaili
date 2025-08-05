package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse

interface LanguageRepository {
    suspend fun getLanguage() :GetLanguageListResponse
    suspend fun getUserType() :GetUserTypeResponse
}