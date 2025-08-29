package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FAQsListResponse(
    @SerialName("paging") var paging: List<Paging>? = null,
    @SerialName("status") var status: Int? = null,
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("response") var response: List<FAQsResponse>? = null,
) {

    @Serializable
    data class Paging(
        @SerialName("pageNumber") var pageNumber: Int? = null,
        @SerialName("pageLimit") var pageLimit: Int? = null,
        @SerialName("totalCount") var totalCount: Int? = null,
        @SerialName("totalPage") var totalPage: Int? = null,
        @SerialName("hasNextPage") var hasNextPage: Boolean? = null,
        @SerialName("hasPreviousPage") var hasPreviousPage: Boolean? = null,

        )

    @Serializable
    data class FAQsResponse(
        @SerialName("slNo") var slNo: Int? = null,
        @SerialName("id") var id: Int? = null,
        @SerialName("name") var name: String? = null,
        @SerialName("description") var description: String? = null,
        @SerialName("projectId") var projectId: Int? = null,
        @SerialName("projectName") var projectName: String? = null,
        @SerialName("usertypeId") var usertypeId: Int? = null,
        @SerialName("usertypeName") var usertypeName: String? = null,
        @SerialName("categoryId") var categoryId: Int? = null,
        @SerialName("categoryName") var categoryName: String? = null,
        @SerialName("subCategoryId") var subCategoryId: Int? = null,
        @SerialName("subCategoryName") var subCategoryName: String? = null,
        @SerialName("priority") var priority: Int? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("createdBy") var createdBy: Int? = null,
        @SerialName("createdDate") var createdDate: String? = null,

        )
}