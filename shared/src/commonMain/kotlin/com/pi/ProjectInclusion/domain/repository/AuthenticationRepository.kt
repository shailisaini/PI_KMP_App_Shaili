package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest

interface AuthenticationRepository {
    suspend fun getLanguage() :GetLanguageListResponse
    suspend fun getUserType() :GetUserTypeResponse
    suspend fun getOTPOnCall(mobNo : String) : SendOTPResponse
    suspend fun getUserLoginPasswordRepo(loginRequest: LoginRequest) : LoginApiResponse
    suspend fun getOTPOnWhatsapp(mobNo : String) : SendOTPResponse
    suspend fun getValidate(userName : String, userTypeId : String) : ValidateUserResponse
    suspend fun getVerifyOtpRepo(userName : String, userTypeId : String) : VerifyOtpResponse
    suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest) : LoginApiResponse
}