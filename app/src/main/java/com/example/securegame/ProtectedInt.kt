package com.example.securegame

import java.security.SecureRandom

/**
 * Demonstração didática de mascaramento de um inteiro em memória.
 *
 * Isto não substitui um servidor autoritativo, criptografia adequada ou
 * uma defesa contra um processo privilegiado. O objetivo é evitar que o
 * valor fique armazenado diretamente como um literal simples.
 */
class ProtectedInt(initialValue: Int = 0) {
    private val random = SecureRandom()
    private var mask: Int = random.nextInt()
    private var maskedValue: Int = initialValue xor mask

    @Synchronized
    fun get(): Int = maskedValue xor mask

    @Synchronized
    fun set(value: Int) {
        mask = random.nextInt()
        maskedValue = value xor mask
    }

    @Synchronized
    fun add(delta: Int) {
        set(get() + delta)
    }
}
