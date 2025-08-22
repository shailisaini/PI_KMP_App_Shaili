package com.pi.ProjectInclusion.domain.useCases

import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.domain.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashboardUsesCases(private val repository: DashboardRepository) {

    var unableToConnectServer: String = "Unable to connect to server"

    fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): Flow<Result<CertificateListResponse>> = flow {
        try {
            val response = repository.getLMSUserCertificateRepo(certificateRequest, strToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            val errorMessage = e.message ?: unableToConnectServer
            logger.d("Exception in certificate $errorMessage")
            emit(Result.failure(Exception(errorMessage)))
        }
    }.flowOn(Dispatchers.IO)

}