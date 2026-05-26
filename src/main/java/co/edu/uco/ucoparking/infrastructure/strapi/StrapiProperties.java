package co.edu.uco.ucoparking.infrastructure.strapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Conexión al CMS Strapi (mensajes de API, textos de interfaz y plantillas de correo).
 */
@ConfigurationProperties(prefix = "strapi")
public class StrapiProperties {

    /** URL base, sin barra final. Ej: http://localhost:1337 */
    private String baseUrl = "http://localhost:1337";

    /** Token de solo lectura (Settings → API Tokens en Strapi). Puede quedar vacío si las colecciones son públicas. */
    private String apiToken = "";

    /** Slug plural REST de la colección de mensajes de error API (código UCOPARKING-*). */
    private String collectionApiMessages = "mensaje-apis";

    /** Slug plural REST de textos de interfaz (clave → valor). */
    private String collectionUiMessages = "mensaje-uis";

    /**
     * Si es true, la aplicación no arranca si no se pueden cargar los mensajes desde Strapi.
     * En desarrollo suele ser false hasta tener Strapi y datos publicados.
     */
    private boolean failOnStartup = false;

    /** Intervalo entre refrescos automáticos del catálogo (ms). 0 = no programa refresco periódico. */
    private long refreshMs = 300_000L;

    /**
     * Si es {@code false}, no se realizan peticiones HTTP a Strapi (catálogo vacío). Útil en tests o arranques sin CMS.
     */
    private boolean remoteEnabled = true;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(final String apiToken) {
        this.apiToken = apiToken;
    }

    public String getCollectionApiMessages() {
        return collectionApiMessages;
    }

    public void setCollectionApiMessages(final String collectionApiMessages) {
        this.collectionApiMessages = collectionApiMessages;
    }

    public String getCollectionUiMessages() {
        return collectionUiMessages;
    }

    public void setCollectionUiMessages(final String collectionUiMessages) {
        this.collectionUiMessages = collectionUiMessages;
    }

    public boolean isFailOnStartup() {
        return failOnStartup;
    }

    public void setFailOnStartup(final boolean failOnStartup) {
        this.failOnStartup = failOnStartup;
    }

    public long getRefreshMs() {
        return refreshMs;
    }

    public void setRefreshMs(final long refreshMs) {
        this.refreshMs = refreshMs;
    }

    public boolean isRemoteEnabled() {
        return remoteEnabled;
    }

    public void setRemoteEnabled(final boolean remoteEnabled) {
        this.remoteEnabled = remoteEnabled;
    }
}
