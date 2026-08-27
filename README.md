# Secure Game Education

Aplicativo Android educacional que demonstra práticas defensivas de segurança em jogos conectados.

## Guia de instalação

### Baixar e instalar o APK

1. Acesse o repositório: https://github.com/GuilhermeXweb/secure-game-android-app
2. Abra a aba **Actions** e selecione a execução mais recente concluída com sucesso.
3. Na seção **Artifacts**, baixe o arquivo `app-debug` e extraia o `app-debug.apk`.
4. Transfira o APK para o celular usando USB, e-mail, Google Drive ou WhatsApp.
5. Abra o arquivo no celular e toque em **Instalar**.
6. Se solicitado, permita a instalação desta fonte em Configurações > Segurança.

### Instalar via USB com Android Studio

1. Conecte o celular ao computador por USB.
2. Ative as Opções do desenvolvedor tocando sete vezes em **Número da compilação**.
3. Ative **Depuração USB** nas Opções do desenvolvedor.
4. Execute na pasta do projeto:

```powershell
.\gradlew.bat installDebug
```

### Requisitos

- Android 8.0 ou superior (API 26+)
- Aproximadamente 50 MB de espaço livre
- Permissão para instalar APKs manualmente

## O aplicativo demonstra

- Armazenamento local criptografado com AES-GCM e Android Keystore
- Validação da integridade do certificado do aplicativo
- Proteção de dados em memória
- Comunicação com servidor autoritário

## Solução de problemas

- **Aplicativo não instala:** desinstale uma versão anterior e verifique o espaço disponível.
- **Fonte desconhecida:** permita a instalação para o aplicativo usado para abrir o APK.
- **Arquivo corrompido:** baixe o artefato novamente no GitHub Actions.

Para o guia detalhado, consulte [README_INSTALACAO.md](README_INSTALACAO.md).