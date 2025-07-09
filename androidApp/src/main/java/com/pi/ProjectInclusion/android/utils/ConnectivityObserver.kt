package com.pi.ProjectInclusion.android.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
    fun getCurrentStatus(): Status

    enum class Status {
        Available, Unavailable, Lost
    }
}