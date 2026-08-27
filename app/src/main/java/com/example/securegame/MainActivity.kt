package com.example.securegame

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var status: TextView
    private lateinit var secureStorage: SecureStorage
    private val backgroundColor = Color.rgb(10, 20, 31)
    private val panelColor = Color.rgb(27, 45, 61)
    private val accentColor = Color.rgb(55, 210, 177)
    private val primaryTextColor = Color.rgb(239, 247, 247)
    private val secondaryTextColor = Color.rgb(161, 183, 190)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        secureStorage = SecureStorage(this)
        status = TextView(this).apply {
                textSize = 13f
                typeface = Typeface.MONOSPACE
                setTextColor(secondaryTextColor)
                setPadding(18, 16, 18, 16)
                background = roundedBackground(panelColor, 14)
        }

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 28, 22, 30)
            setBackgroundColor(backgroundColor)
        }

        root.addView(TextView(this).apply {
            text = "SECURE GAME / LAB"
            textSize = 12f
            letterSpacing = 0.12f
            setTextColor(accentColor)
            typeface = Typeface.DEFAULT_BOLD
        })
        root.addView(TextView(this).apply {
            text = "Seguranca que joga\nno seu time"
            textSize = 30f
            setTextColor(primaryTextColor)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 12, 0, 8)
        })
        root.addView(TextView(this).apply {
            text = "Uma demonstracao pratica de protecao para jogos conectados."
            textSize = 14f
            setTextColor(secondaryTextColor)
            setPadding(0, 0, 0, 22)
        })
        root.addView(status)

        root.addView(Button(this).apply {
            text = "Executar demonstração local"
            isAllCaps = false
            textSize = 14f
            setTextColor(backgroundColor)
            background = roundedBackground(accentColor, 12)
            setPadding(12, 0, 12, 0)
            setOnClickListener { runLocalDemo() }
        })

        root.addView(Button(this).apply {
            text = "Verificar assinatura instalada"
            isAllCaps = false
            textSize = 14f
            setTextColor(primaryTextColor)
            background = roundedBackground(panelColor, 12)
            setPadding(12, 0, 12, 0)
            setOnClickListener { runIntegrityCheck() }
        })

        setContentView(root)
        runLocalDemo()
    }

    private fun roundedBackground(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius.toFloat()
        }
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
