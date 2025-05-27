package com.example.pi_kmp_application

class Greeting {
    private val platform: Platform = getPlatform()
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}