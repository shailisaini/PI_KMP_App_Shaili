package com.pi.ProjectInclusion

class IOSPlatform: Platform {
    override val name: String = "Platform"
}

actual fun getPlatform(): Platform = IOSPlatform()