package com.pi.ProjectInclusion.data.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.FAQsListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryByCategoryIdResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryListResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.domain.repository.DashboardRepository

class DashboardRepoImpl(private val apiService: ApiService) : DashboardRepository {

    override suspend fun getLMSUserCertificateRepo(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): CertificateListResponse {
        return apiService.getLMSUserCertificate(certificateRequest, strToken)
    }

    override suspend fun getAllCategoryRepo(): List<CategoryListResponse> {
        return apiService.getAllCategory()
    }

    override suspend fun getAllSubCategoryRepo(): List<SubCategoryListResponse> {
        return apiService.getAllSubCategory()
    }

    override suspend fun getAllSubCategoryByCategoryIdRepo(categoryId: Int): SubCategoryByCategoryIdResponse {
        return apiService.getAllSubCategoryByCategoryId(categoryId)
    }

    override suspend fun getAllFAQsRepo(
        strKeyword: String,
        userTypeId: String,
        categoryId: String,
        subCategoryId: String,
        userId: String,
        languageId: String,
    ): FAQsListResponse {
        return apiService.getAllFAQs(
            strKeyword, userTypeId, categoryId, subCategoryId, userId, languageId
        )
    }
}