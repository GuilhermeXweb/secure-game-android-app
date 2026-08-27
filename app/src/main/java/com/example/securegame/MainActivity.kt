package com.example.securegame

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var status: TextView
    private lateinit var secureStorage: SecureStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        secureStorage = SecureStorage(this)
        status = TextView(this).apply {
            textSize = 16f
            setPadding(32, 24, 32, 24)
        }

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        root.addView(TextView(this).apply {
            text = "Secure Game Education"
            textSize = 24f
        })
        root.addView(status)

        root.addView(Button(this).apply {
            text = "Executar demonstração local"
            setOnClickListener { runLocalDemo() }
        })

        root.addView(Button(this).apply {
            text = "Verificar assinatura instalada"
            setOnClickListener { runIntegrityCheck() }
        })

        setContentView(root)
        runLocalDemo()
    }

    private fun runLocalDemo() {
        val protectedScore = ProtectedInt(100)
        protectedScore.add(25)

        secureStorage.putString("session_marker", "cliente-demo-local")
        val marker = secureStorage.getString("session_marker") ?: "indisponível"

        status.text = buildString {
            appendLine("Sandbox do processo: ativo por padrão")
            appendLine("Diretório privado: ${filesDir.absolutePath}")
            appendLine("Marcador protegido recuperado: $marker")
            appendLine("Valor didático recuperado do ProtectedInt: ${protectedScore.get()}")
            appendLine()
            appendLine("Importante: pontuação, dano e posição não devem ser aceitos como fatos enviados pelo cliente.")
        }
    }

    private fun runIntegrityCheck() {
        // Substitua pelo SHA-256 do certificado de release obtido no processo de build.
        val expectedCertificateSha256 = "COLOQUE_AQUI_O_SHA256_DO_CERTIFICADO_DE_RELEASE"
        val result = IntegrityChecker(this).check(expectedCertificateSha256)

        status.text = buildString {
            appendLine("Pacote: ${result.packageName}")
            appendLine("Certificado instalado: ${result.certificateSha256 ?: "não identificado"}")
            appendLine("Corresponde ao esperado: ${result.matchesExpected}")
            result.error?.let { appendLine("Erro: $it") }
            appendLine()
            appendLine("Esta checagem é apenas um sinal complementar. A decisão de confiança deve permanecer no servidor.")
        }
    }
}
