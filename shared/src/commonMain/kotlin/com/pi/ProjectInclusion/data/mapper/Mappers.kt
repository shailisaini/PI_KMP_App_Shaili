package com.pi.ProjectInclusion.data.mapper

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse

fun List<GetLanguageListResponse>.toDomain() : List<GetLanguageListResponse> = map {
    GetLanguageListResponse(data = it.data, error = it.error, isSuccess = it.isSuccess)
}