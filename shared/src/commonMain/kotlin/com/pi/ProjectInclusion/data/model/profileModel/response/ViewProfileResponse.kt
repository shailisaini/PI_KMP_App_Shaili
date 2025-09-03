package com.pi.ProjectInclusion.data.model.profileModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ViewProfileResponse(
    /* @SerialName("methodType")
     val methodType: String? = null,

     @SerialName("status")
     val status: Boolean? = null,

     @SerialName("statusCode")
     val statusCode: Int? = null,

     @SerialName("message")
     val message: String? = null,

     @SerialName("error")
     val error: String? = null,

     @SerialName("response")
     val response: ProfileResponse? = null,*/

    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: ProfileResponse? = null,
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,

    ) {
    @Serializable
    data class ProfileResponse(

        /* @SerialName("id")
         val id: String? = null,

         @SerialName("username")
         val username: String? = null,

         @SerialName("password")
         val password: String? = null,

         @SerialName("email")
         val email: String? = null,

         @SerialName("mobile")
         val mobile: String? = null,

         @SerialName("loginAttempt")
         val loginAttempt: Int? = null,

         @SerialName("lastlogin")
         val lastLogin: String? = null,

         @SerialName("loginflag")
         val loginFlag: Int? = null,

         @SerialName("userExpInDays")
         val userExpInDays: Int? = null,

         @SerialName("passwordExpInDays")
         val passwordExpInDays: Int? = null,

         @SerialName("isProfileLocked")
         val isProfileLocked: Int? = null,

         @SerialName("registrationSource")
         val registrationSource: String? = null,

         @SerialName("userTypeId")
         val userTypeId: String? = null,

         @SerialName("roleId")
         val roleId: String? = null,

         @SerialName("createdDate")
         val createdDate: String? = null,

         @SerialName("updatedDate")
         val updatedDate: String? = null,

         @SerialName("createdBy")
         val createdBy: String? = null,

         @SerialName("updatedBy")
         val updatedBy: String? = null,

         @SerialName("status")
         val status: Int? = null,

         @SerialName("lastAccountInactiveDate")
         val lastAccountInactiveDate: String? = null,

         @SerialName("lastAccountRestoredDate")
         val lastAccountRestoredDate: String? = null,*/

        @SerialName("userId") var userId: String? = null,
        @SerialName("userDemographicId") var userDemographicId: String? = null,
        @SerialName("username") var username: String? = null,
        @SerialName("password") var password: String? = null,
        @SerialName("firstname") var firstname: String? = null,
        @SerialName("middlename") var middlename: String? = null,
        @SerialName("lastname") var lastname: String? = null,
        @SerialName("dob") var dob: String? = null,
        @SerialName("fathername") var fathername: String? = null,
        @SerialName("mothername") var mothername: String? = null,
        @SerialName("address") var address: String? = null,
        @SerialName("email") var email: String? = null,
        @SerialName("mobile") var mobile: String? = null,
        @SerialName("whatsapp") var whatsapp: String? = null,
        @SerialName("gender") var gender: String? = null,
        @SerialName("countryId") var countryId: String? = null,
        @SerialName("stateId") var stateId: String? = null,
        @SerialName("districtId") var districtId: String? = null,
        @SerialName("divisionId") var divisionId: String? = null,
        @SerialName("blockId") var blockId: String? = null,
        @SerialName("villageId") var villageId: String? = null,
        @SerialName("schoolId") var schoolId: String? = null,
        @SerialName("udicecode") var udicecode: String? = null,
        @SerialName("qualificationId") var qualificationId: String? = null,
        @SerialName("designationId") var designationId: String? = null,
        @SerialName("languageId") var languageId: String? = null,
        @SerialName("languageName") var languageName: String? = null,
        @SerialName("professionId") var professionId: String? = null,
        @SerialName("specializationId") var specializationId: String? = null,
        @SerialName("registrationDate") var registrationDate: String? = null,
        @SerialName("profilepic") var profilepic: String? = null,
        @SerialName("occupationId") var occupationId: String? = null,
        @SerialName("deviceId") var deviceId: String? = null,
        @SerialName("deviceToken") var deviceToken: String? = null,
        @SerialName("CRRNum") var CRRNum: String? = null,
        @SerialName("reasonId") var reasonId: String? = null,
        @SerialName("userTypeId") var userTypeId: String? = null,
        @SerialName("userTypeName") var userTypeName: String? = null,
        @SerialName("roleId") var roleId: String? = null,
        @SerialName("roleName") var roleName: String? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("createdBy") var createdBy: String? = null,
        @SerialName("createdDate") var createdDate: String? = null,
    )
}
