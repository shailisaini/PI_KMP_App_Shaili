package com.pi.ProjectInclusion.domain.useCases

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.domain.repository.LanguageRepository

class GetLanguageUsesCases(private val repository: LanguageRepository) {

    suspend operator fun invoke(page: String, limit: String) :Result<GetLanguageListResponse>{
      return try {
            val response = repository.getLanguage(page, limit)
            Result.success(response)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}