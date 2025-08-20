package com.pi.ProjectInclusion.data.model.authenticationModel.Response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CertificateListResponse(
    @SerialName("status") var status: Int? = null,
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("response") var response: ArrayList<CertificateResponse>? = null,
) {
    @Serializable
    data class CertificateResponse(

        @SerialName("courseCreateID") var courseCreateID: Int? = null,
        @SerialName("certificateID") var certificateID: Int? = null,
        @SerialName("userID") var userID: Int? = null,
        @SerialName("partnerName") var partnerName: String? = null,
        @SerialName("partnerUserId") var partnerUserId: Int? = null,
        @SerialName("firstName") var firstName: String? = null,
        @SerialName("middleName") var middleName: String? = null,
        @SerialName("lastName") var lastName: String? = null,
        @SerialName("description") var description: String? = null,
        @SerialName("isExcellence") var isExcellence: Int? = null,
        @SerialName("isDownloadAvailable") var isDownloadAvailable: String? = null,
        @SerialName("certificatePath") var certificatePath: String? = null,
        @SerialName("appCertificatePath") var appCertificatePath: String? = null,
        @SerialName("createdDate") var createdDate: String? = null,
        @SerialName("ccmPriority") var ccmPriority: Int? = null,
        @SerialName("courseMappingTypeName") var courseMappingTypeName: String? = null,
        @SerialName("courseMappingTypeCategory") var courseMappingTypeCategory: String? = null,
        @SerialName("category") var category: String? = null,
        @SerialName("certificateName") var certificateName: String? = null,
        @SerialName("certificateTitle") var certificateTitle: String? = null,
        @SerialName("certificateTypeName") var certificateTypeName: String? = null,
        @SerialName("organisation") var organisation: String? = null,
        @SerialName("projectName") var projectName: String? = null,
        @SerialName("projectLogo") var projectLogo: String? = null,
        @SerialName("signedBy") var signedBy: String? = null,
        @SerialName("courseId") var courseId: Int? = null,
        @SerialName("courseTitle") var courseTitle: String? = null,
        @SerialName("moduleID") var moduleID: Int? = null,
        @SerialName("moduleTitle") var moduleTitle: String? = null,
        @SerialName("chapterID") var chapterID: Int? = null,
        @SerialName("chapterTitle") var chapterTitle: String? = null,
        @SerialName("courseUniqueKey") var courseUniqueKey: String? = null,

        )
}
