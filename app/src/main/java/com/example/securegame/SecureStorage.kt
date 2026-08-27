package com.example.securegame

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Armazena pequenos valores locais usando uma chave não exportável do Android Keystore.
 * Não use este armazenamento para confiar em decisões de jogo: o dispositivo do
 * usuário continua sob controle do cliente.
 */
class SecureStorage(context: Context) {
    private val preferences = context.getSharedPreferences(
        "secure_game_storage",
        Context.MODE_PRIVATE
    )

    private val keyAlias = "secure_game_aes_key_v1"
    private val keyStoreName = "AndroidKeyStore"
    private val transformation = "AES/GCM/NoPadding"

    init {
        ensureKey()
    }

    fun putString(name: String, value: String) {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val ciphertext = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        val packed = cipher.iv + ciphertext

        preferences.edit()
            .putString(name, Base64.encodeToString(packed, Base64.NO_WRAP))
            .apply()
    }

    fun getString(name: String): String? {
        val encoded = preferences.getString(name, null) ?: return null
        val packed = Base64.decode(encoded, Base64.NO_WRAP)
        require(packed.size > 12) { "Payload criptografado inválido" }

        val iv = packed.copyOfRange(0, 12)
        val ciphertext = packed.copyOfRange(12, packed.size)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(128, iv))
        return cipher.doFinal(ciphertext).toString(StandardCharsets.UTF_8)
    }

    fun remove(name: String) {
        preferences.edit().remove(name).apply()
    }

    private fun ensureKey() {
        val keyStore = KeyStore.getInstance(keyStoreName).apply { load(null) }
        if (keyStore.containsAlias(keyAlias)) return

        val generator = KeyGenerator.getInstance("AES", keyStoreName)
        val specification = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        generator.init(specification)
        generator.generateKey()
    }

    private fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(keyStoreName).apply { load(null) }
        return (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
    }
}
