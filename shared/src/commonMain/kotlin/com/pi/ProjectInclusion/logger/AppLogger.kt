package com.example.kmptemplate.logger

import co.touchlab.kermit.Logger

interface AppLogger{

    fun d(message: String)

    fun e(message: String)

    fun e(message: String, throwable: Throwable?)

    fun w(message: String)
}

class AppLoggerImpl : AppLogger{
    override fun d(message: String) {
        Logger.d { message }
    }

    override fun e(message: String) {
        Logger.e { message }
    }

    override fun e(message: String, throwable: Throwable?) {
        Logger.e(throwable){ message }
    }

    override fun w(message: String) {
        Logger.w { message }
    }
}

//Singleton object
object LoggerProvider {
    val logger: AppLogger = AppLoggerImpl()
}