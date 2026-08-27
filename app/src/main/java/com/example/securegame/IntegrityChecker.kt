package com.example.securegame

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest

/**
 * Compara o certificado de assinatura instalado com um digest conhecido.
 * O digest esperado deve ser injetado por configuração segura no processo de build.
 */
class IntegrityChecker(private val context: Context) {
    data class Result(
        val packageName: String,
        val certificateSha256: String?,
        val matchesExpected: Boolean,
        val error: String? = null
    )

    fun check(expectedSha256: String): Result {
        return try {
            val packageManager = context.packageManager
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
                )
            }

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures
            }

            val actual = signatures.firstOrNull()?.toByteArray()?.let(::sha256Hex)
            val normalizedExpected = expectedSha256
                .replace(":", "")
                .replace(" ", "")
                .uppercase()

            Result(
                packageName = context.packageName,
                certificateSha256 = actual,
                matchesExpected = actual != null && actual.equals(normalizedExpected, ignoreCase = true)
            )
        } catch (exception: Exception) {
            Result(
                packageName = context.packageName,
                certificateSha256 = null,
                matchesExpected = false,
                error = exception.javaClass.simpleName
            )
        }
    }

    private fun sha256Hex(bytes: ByteArray): String = MessageDigest
        .getInstance("SHA-256")
        .digest(bytes)
        .joinToString("") { byte -> "%02X".format(byte) }
}
