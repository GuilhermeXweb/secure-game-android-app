# 📱 Secure Game - Guia de Instalação

## O que é este aplicativo?

**Secure Game Education** é um aplicativo educacional de Android que demonstra práticas defensivas de segurança em jogos conectados.

O aplicativo inclui:
- ✅ Armazenamento criptografado local (SecureStorage com AES-GCM)
- ✅ Validação de integridade do certificado
- ✅ Proteção de dados em memória (ProtectedInt)
- ✅ Comunicação segura com servidor autoritário

---

## 📥 Como instalar no seu celular

### **Método 1: Download do APK (Recomendado)**

1. **Acesse o repositório GitHub:**
   - Abra: https://github.com/GuilhermeXweb/secure-game-android-app

2. **Baixe o APK compilado:**
   - Clique em **Actions** (aba no repositório)
   - Procure pela ação mais recente com o ✅ **verde**
   - Clique nela
   - Role para baixo e clique em **app-debug** (sob "Artifacts")
   - Extraia o arquivo `app-debug.apk`

3. **Transfira para seu celular:**
   - Via email, WhatsApp, Google Drive, ou conecte por USB

4. **Instale no celular:**
   - Abra o arquivo `app-debug.apk`
   - Toque em **"Instalar"**
   - Se pedir permissão de "Fontes desconhecidas", ative em Configurações > Segurança

---

### **Método 2: Instalação via USB (Desenvolvedores)**

Se tem Android SDK/Android Studio instalado:

1. **Conecte o celular via USB**

2. **Ative Modo de Desenvolvedor:**
   - Configurações > Sobre o telefone
   - Toque 7 vezes em "Número da compilação"

3. **Ative Depuração USB:**
   - Volte para Configurações > Opções do desenvolvedor
   - Ative "Depuração USB"

4. **Rode o comando:**
   ```bash
   cd c:\Users\Admin\Desktop\secure-game-android-app
   .\gradlew.bat installDebug
   ```

---

## 🎮 Como usar o aplicativo

### **Tela inicial - Demonstração Local**

Quando abrir, você verá:
- **Caminho privado do app:** Mostra onde os dados são armazenados
- **Armazenamento seguro:** Demonstra criptografia local
- **Valor protegido:** Mostra proteção de dados em memória

### **Botão "Verificar assinatura instalada"**

- Valida o certificado de assinatura do APK
- Compara com o fingerprint esperado
- Indica se o aplicativo foi modificado

---

## ⚙️ Requisitos

- ✅ Android 8.0 ou superior (API 26+)
- ✅ Aproximadamente 50MB de espaço livre
- ✅ Permissão para instalar de fontes desconhecidas (para APK manual)

---

## 🔐 Nota de Segurança

**Este é um aplicativo educacional.**

- Nunca confie dados críticos apenas no cliente
- Sempre valide e calcule dados importantes no servidor
- Use HTTPS para comunicação
- O Android Keystore protege chaves, mas não torna o cliente confiável

---

## 📚 Para Desenvolvedores

Se quer modificar o código:

1. **Abra em Android Studio:**
   - File > Open > Selecione a pasta `secure-game-android-app`

2. **Sincronize Gradle:**
   - Deixe o Android Studio sincronizar automaticamente

3. **Rode em um emulador:**
   - Clique em ▶️ "Run" na barra superior

---

## 🐛 Troubleshooting

### Erro: "Aplicativo não pode ser instalado"
- Desinstale versão anterior
- Verifique espaço disponível no celular
- Tente novamente

### Erro: "Fonte desconhecida"
- Ative em: Configurações > Segurança > Fontes desconhecidas

### Erro: "Arquivo corrompido"
- Baixe novamente o APK do GitHub Actions

---

## 📞 Suporte

- 💻 Repositório: https://github.com/GuilhermeXweb/secure-game-android-app
- 📖 Documentação: Veja `ARQUITETURA.md` no repositório

---

**Aproveite e aprenda sobre segurança em Android! 🚀**
