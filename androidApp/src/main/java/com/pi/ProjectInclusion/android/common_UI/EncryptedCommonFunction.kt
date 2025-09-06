package com.pi.ProjectInclusion.android.common_UI

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.decrypt
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES

object EncryptedCommonFunction {

    // check if encryptedEmail
    @Composable
    fun decryptedEmail(email: String): String {
        var shownEmail = ""

        val isEncrypted = isEncryptedEmail(email.trim())
        shownEmail = if (isEncrypted) {
            decrypt(email)
        } else {
            email
        }
        return shownEmail
    }

    @Composable
    fun encryptedEmail(email: String): String {
        var shownEmail = ""

        val isEncrypted = isEncryptedEmail(email.trim())
        shownEmail = if (isEncrypted) {
            email
        } else {
            email.encryptAES().toString().trim()
        }
        return shownEmail
    }

    // return decrypted mobile No
    @Composable
    fun decryptedPhoneNo(phoneNo: String): String {
        var shownPhoneNo = ""

        val isEncrypted = isEncryptedPhone(phoneNo.trim())
        shownPhoneNo = if (isEncrypted) {
            decrypt(phoneNo)
        } else {
            phoneNo
        }
        return shownPhoneNo
    }


//    return encrypted
    @Composable
    fun encryptedPhoneNo(phoneNo: String): String {
        var shownPhoneNo = ""

        val isEncrypted = isEncryptedPhone(phoneNo.trim())
        shownPhoneNo = if (isEncrypted) {
            phoneNo
        } else {
            phoneNo.encryptAES().toString().trim()
        }
        return shownPhoneNo
    }

    fun isEncryptedEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return !(emailPattern.matches(email))
    }

    fun isEncryptedPhone(data: String): Boolean {
        val phonePattern = Regex("^\\+?[0-9]{10,15}$")
        return !phonePattern.matches(data.trim())
    }
}
