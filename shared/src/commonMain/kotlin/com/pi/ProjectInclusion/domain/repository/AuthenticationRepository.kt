package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.SendOTPResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.ValidateUserResponse

interface AuthenticationRepository {
    suspend fun getLanguage() :GetLanguageListResponse
    suspend fun getUserType() :GetUserTypeResponse
    suspend fun getOTPOnCall(mobNo : String) : SendOTPResponse
    suspend fun getOTPOnWhatsapp(mobNo : String) : SendOTPResponse
    suspend fun getValidate(userName : String, userTypeId : String) : ValidateUserResponse
}