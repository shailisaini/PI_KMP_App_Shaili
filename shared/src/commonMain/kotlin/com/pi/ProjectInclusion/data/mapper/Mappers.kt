package com.pi.ProjectInclusion.data.mapper

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse

fun List<GetLanguageListResponse>.toDomain() : List<GetLanguageListResponse> = map {
    GetLanguageListResponse(message = it.message,
        statusCode = it.statusCode,
        response = it.response,
        status = it.status,)
}