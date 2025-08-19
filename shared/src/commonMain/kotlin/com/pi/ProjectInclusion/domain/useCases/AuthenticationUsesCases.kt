package com.pi.ProjectInclusion.domain.useCases

import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthenticationUsesCases(private val repository: AuthenticationRepository) {

    var unableToConnectServer: String = "Unable to connect to server"

    fun getLanguage(): Flow<Result<GetLanguageListResponse>> = flow {
        try {
            val response = repository.getLanguage()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getLanguage() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getUserType(): Flow<Result<GetUserTypeResponse>> = flow {
        try {
            val response = repository.getUserType()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getUserType() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getValidateUserCase(
        userName: String,
        userTypeId: String,
    ): Flow<Result<ValidateUserResponse>> = flow {
        try {
            val response = repository.getValidate(userName, userTypeId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in validateUser() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getOtpOnCall(mobNo: String): Flow<Result<SendOTPResponse>> = flow {
        try {
            val response = repository.getOTPOnCall(mobNo)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnCall() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getUserLoginPassword(loginRequest: LoginRequest): Flow<Result<LoginApiResponse>> = flow {
        try {
            val response = repository.getUserLoginPasswordRepo(loginRequest)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in LoginPassword() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getOTPOnWhatsapp(mobNo: String): Flow<Result<SendOTPResponse>> = flow {
        try {
            val response = repository.getOTPOnWhatsapp(mobNo)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnWhatsapp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun forgetPassword(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): Flow<Result<ForgetPasswordResponse>> = flow {
        try {
            val response = repository.forgetPasswordRepo(passwordRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnWhatsapp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun createRegisterPassword(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ): Flow<Result<CreateRegisterPasswordResponse>> = flow {
        try {
            val response = repository.createRegisterPasswordRepo(passwordRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnWhatsapp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getVerifyOtp(mobNo: String, otpValue: String): Flow<Result<VerifyOtpResponse>> = flow {
        try {
            val response = repository.getVerifyOtpRepo(mobNo, otpValue)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnWhatsapp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getLoginWithOtp(request: LoginWithOtpRequest): Flow<Result<LoginApiResponse>> = flow {
        try {
            val response = repository.getLoginWithOTPRepo(request)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnWhatsapp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

}

//private fun getErrorMessage(errorBody: String?): String {
//    return try {
//        val jsonObject = JSONObject(errorBody ?: "")
//        jsonObject.getString("error") ?: "Unknown error"
//    } catch (e: JSONException) {
//        "An error occurred."
//    }
//}