package co.edu.uco.ucoparking.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Resuelve el issuer de Auth0 para el resource server: {@code AUTH0_ISSUER_URI} tiene prioridad;
 * si no está definido, se construye desde {@code AUTH0_DOMAIN} (p. ej. variables sincronizadas desde Infisical).
 */
@Component
public final class Auth0RuntimeSettings {

    private final String issuerLocation;

    public Auth0RuntimeSettings(
            @Value("${uco.auth0.issuer-uri:}") final String issuerUri,
            @Value("${uco.auth0.domain:}") final String auth0Domain) {
        this.issuerLocation = resolveIssuerLocation(issuerUri, auth0Domain);
    }

    private static String resolveIssuerLocation(final String issuerUri, final String auth0Domain) {
        if (issuerUri != null && !issuerUri.isBlank()) {
            return issuerUri.trim();
        }
        if (auth0Domain == null || auth0Domain.isBlank()) {
            return "";
        }
        final String host = auth0Domain.trim().replaceFirst("(?i)^https?://", "").replaceAll("/+$", "");
        if (host.isBlank()) {
            return "";
        }
        return "https://" + host + "/";
    }

    /**
     * @return {@code true} si hay issuer válido y las rutas {@code /api/v1/**} exigen JWT.
     */
    public boolean isJwtSecurityEnabled() {
        return !issuerLocation.isBlank();
    }

    public String getIssuerLocation() {
        return issuerLocation;
    }
}
