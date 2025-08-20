package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.Response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest

interface DashboardRepository {

    suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): CertificateListResponse
}