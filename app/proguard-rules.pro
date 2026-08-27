# A ofuscação dificulta engenharia reversa casual, mas não transforma o cliente em uma autoridade confiável.
# Regras específicas da aplicação podem ser adicionadas conforme novos componentes forem incluídos.

# Não registrar segredos, chaves ou marcadores em logs de release.
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
