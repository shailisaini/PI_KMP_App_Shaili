package com.example.pi_kmp_application

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform