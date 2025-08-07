package com.pi.ProjectInclusion.data.model

data class CommonResponse(
    val errorCode: String?,
    val message: String?,
    val response: List<Any>?=null,
    val status: Int?
)