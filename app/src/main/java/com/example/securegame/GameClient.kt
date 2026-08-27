package com.example.securegame

/** Tipos de intenção que o cliente pode solicitar ao servidor. */
enum class ActionType {
    MOVE,
    ATTACK
}

data class ActionIntent(
    val playerId: String,
    val nonce: String,
    val type: ActionType,
    val targetId: String? = null,
    val directionX: Int = 0,
    val directionY: Int = 0
)

data class AuthoritativeState(
    val playerId: String,
    val x: Int,
    val y: Int,
    val health: Int,
    val score: Int,
    val sequence: Long
)

interface AuthoritativeGateway {
    fun submit(intent: ActionIntent): Result<AuthoritativeState>
}

/**
 * Fachada pequena para o cliente Android.
 * O cliente constrói intenções e renderiza o último estado aceito; ele não calcula
 * dano, pontuação ou posição final como autoridade.
 */
class GameClient(private val gateway: AuthoritativeGateway) {
    private var lastState: AuthoritativeState? = null

    fun requestMove(playerId: String, nonce: String, dx: Int, dy: Int): Result<AuthoritativeState> =
        submit(ActionIntent(playerId, nonce, ActionType.MOVE, directionX = dx, directionY = dy))

    fun requestAttack(playerId: String, nonce: String, targetId: String): Result<AuthoritativeState> =
        submit(ActionIntent(playerId, nonce, ActionType.ATTACK, targetId = targetId))

    fun currentState(): AuthoritativeState? = lastState

    private fun submit(intent: ActionIntent): Result<AuthoritativeState> {
        val response = gateway.submit(intent)
        response.onSuccess { state -> lastState = state }
        return response
    }
}
