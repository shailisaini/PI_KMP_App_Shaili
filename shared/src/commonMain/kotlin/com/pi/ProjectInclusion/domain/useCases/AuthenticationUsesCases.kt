package com.pi.ProjectInclusion.domain.useCases

import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ProfessionListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.QualificationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ReasonListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SpecializationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
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
            LoggerProvider.logger.d("Exception in forgetPassword() $errorMessage")
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
            LoggerProvider.logger.d("Exception in createRegisterPassword() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getVerifyOtp(mobNo: String, otpValue: String): Flow<Result<VerifyOtpResponse>> = flow {
        try {
            val response = repository.getVerifyOtpRepo(mobNo, otpValue)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getVerifyOtp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getLoginWithOtp(request: LoginWithOtpRequest): Flow<Result<LoginApiResponse>> = flow {
        try {
            val response = repository.getLoginWithOTPRepo(request)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getLoginWithOtp() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getViewUserProfile(userName: String): Flow<Result<ViewProfileResponse>> = flow {
        try {
            val response = repository.getUserProfileRepo(userName)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getViewUserProfile() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null,
    ): Flow<Result<CreateFirstStepProfileResponse>> = flow {
        try {
            val response = repository.createFirstStepProfileRepo(
                firstStepProfileRequest, strToken, profilePic, fileName
            )
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in createFirstStepProfileRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllStateListRepo(): Flow<Result<List<StateListResponse>>> = flow {
        try {
            val response = repository.getAllStateListRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllStateListRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllDistrictByStateIdRepo(stateId: Int): Flow<Result<List<DistrictListResponse>>> = flow {
        try {
            val response = repository.getAllDistrictByStateIdRepo(stateId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllDistrictByStateIdRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllBlockByDistrictIdRepo(districtId: Int): Flow<Result<List<BlockListResponse>>> = flow {
        try {
            val response = repository.getAllBlockByDistrictIdRepo(districtId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllBlockByDistrictIdRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllSchoolsByBlockIdRepo(blockId: Int): Flow<Result<SchoolListResponse>> = flow {
        try {
            val response = repository.getAllSchoolsByBlockIdRepo(blockId)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllSchoolsByBlockIdRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllDetailsByUdiseIdRepo(udiseCode: String): Flow<Result<SchoolByUdiseCodeResponse>> =
        flow {
            try {
                val response = repository.getAllDetailsByUdiseIdRepo(udiseCode)
                emit(Result.success(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: unableToConnectServer
                LoggerProvider.logger.d("Exception in getAllDetailsByUdiseIdRepo() $errorMessage")
                emit(Result.failure(Exception(errorMessage)))
            }
        }.flowOn(Dispatchers.IO)

    fun createProfessionalProfileRepo(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ): Flow<Result<CreateFirstStepProfileResponse>> = flow {
        try {
            val response =
                repository.createProfessionalProfileRepo(professionalProfileRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in createProfessionalProfileRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllProfessionRepo(): Flow<Result<List<ProfessionListResponse>>> = flow {
        try {
            val response = repository.getAllProfessionRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllProfessionRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllQualificationRepo(profession: Int): Flow<Result<List<QualificationListResponse>>> =
        flow {
            try {
                val response = repository.getAllQualificationRepo(profession)
                emit(Result.success(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: unableToConnectServer
                LoggerProvider.logger.d("Exception in getAllQualificationRepo() $errorMessage")
                emit(Result.failure(Exception(errorMessage)))
            }
        }.flowOn(Dispatchers.IO)

    fun getAllSpecializationRepo(
        profession: Int,
        qualification: Int,
    ): Flow<Result<List<SpecializationListResponse>>> = flow {
        try {
            val response = repository.getAllSpecializationRepo(profession, qualification)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllSpecializationRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllReasonRepo(): Flow<Result<ReasonListResponse>> = flow {
        try {
            val response = repository.getAllReasonRepo()
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            LoggerProvider.logger.d("Exception in getAllReasonRepo() $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)
}