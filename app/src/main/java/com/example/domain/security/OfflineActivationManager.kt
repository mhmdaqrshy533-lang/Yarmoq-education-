package com.example.domain.security

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.MessageDigest

class OfflineActivationManager(private val context: Context) {

    private val _activationState = MutableStateFlow(ActivationState.UNREGISTERED)
    val activationState: StateFlow<ActivationState> = _activationState.asStateFlow()

    @SuppressLint("HardwareIds")
    fun getDeviceSerialCode(): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return hashString(androidId).substring(0, 10).uppercase()
    }

    fun verifyActivationCode(enteredCode: String): Boolean {
        val expectedCode = generateExpectedCode(getDeviceSerialCode())
        val isValid = enteredCode.equals(expectedCode, ignoreCase = true)
        if (isValid) {
            _activationState.value = ActivationState.PRO_ACTIVATED
            // Future: Save to encrypted Room DB
        }
        return isValid
    }

    private fun generateExpectedCode(serial: String): String {
        // Simple offline algorithm: Hash the serial with a "secret" salt
        val salt = "YARMOUK_OFFLINE_SALT_2026"
        return hashString(serial + salt).substring(0, 12).uppercase()
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

enum class ActivationState {
    UNREGISTERED,
    PRO_ACTIVATED
}
