package com.pi.ProjectInclusion.domain.useCases

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.domain.repository.LanguageRepository

class GetLanguageUsesCases(private val repository: LanguageRepository) {

    suspend fun getLanguage(page: String, limit: String) :Result<GetLanguageListResponse>{
      return try {
            val response = repository.getLanguage(page, limit)
            Result.success(response)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun getUserType() :Result<GetUserTypeResponse>{
      return try {
            val response = repository.getUserType()
            Result.success(response)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}