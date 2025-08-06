package com.pi.ProjectInclusion.domain

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
    fun getCurrentStatus(): Status

    enum class Status {
        Available, Unavailable, Lost
    }
}