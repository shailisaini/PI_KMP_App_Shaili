package com.pi.ProjectInclusion.domain.useCases

import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.SendOTPResponse
import com.pi.ProjectInclusion.domain.repository.LanguageRepository
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLanguageUsesCases(private val repository: LanguageRepository) {

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

    fun getOtpOnCall(mobNo : String): Flow<Result<SendOTPResponse>> = flow {
        try {
            val response = repository.getOTPOnCall(mobNo)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in otpOnCall() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getOTPOnWhatsapp(mobNo : String): Flow<Result<SendOTPResponse>> = flow {
        try {
            val response = repository.getOTPOnWhatsapp(mobNo)
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