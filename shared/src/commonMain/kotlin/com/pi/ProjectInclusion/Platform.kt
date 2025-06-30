package com.pi.ProjectInclusion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform