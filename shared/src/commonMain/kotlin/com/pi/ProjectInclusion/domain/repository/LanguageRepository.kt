package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.SendOTPResponse

interface LanguageRepository {
    suspend fun getLanguage() :GetLanguageListResponse
    suspend fun getUserType() :GetUserTypeResponse
    suspend fun getOTPOnCall(mobNo : String) : SendOTPResponse
    suspend fun getOTPOnWhatsapp(mobNo : String) : SendOTPResponse
}