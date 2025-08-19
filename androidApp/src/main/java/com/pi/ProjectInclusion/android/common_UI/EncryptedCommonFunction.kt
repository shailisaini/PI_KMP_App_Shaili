package com.pi.ProjectInclusion.android.common_UI

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.decrypt

object EncryptedCommonFunction {

    // check if encryptedEmail
    @Composable
    fun email(email: String): String {
        var shownEmail = ""

        val isEncrypted = remember { isEncryptedEmail(email) }
        shownEmail = if (isEncrypted) {
            decrypt(email)
        } else {
            email
        }
        return shownEmail
    }

    // check if encryptedPhone
    @Composable
    fun phoneNo(phoneNo: String): String {
        var shownPhoneNo = ""

        val isEncrypted = remember { isEncryptedPhone(phoneNo) }
        shownPhoneNo = if (isEncrypted) {
            decrypt(phoneNo)
        } else {
            phoneNo
        }
        return shownPhoneNo
    }

    fun isEncryptedEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return !(emailPattern.matches(email))
    }

    fun isEncryptedPhone(data: String): Boolean {
        val phonePattern = Regex("^\\+?[0-9]{10,15}$")
        return !(phonePattern.matches(data))
    }
}
