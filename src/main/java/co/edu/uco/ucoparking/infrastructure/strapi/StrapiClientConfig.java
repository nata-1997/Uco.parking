package co.edu.uco.ucoparking.infrastructure.strapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class StrapiClientConfig {

    @Bean(name = "strapiRestClient")
    public RestClient strapiRestClient(final StrapiProperties properties) {
        final String base = trimTrailingSlash(properties.getBaseUrl());
        final RestClient.Builder builder = RestClient.builder().baseUrl(base);
        // Strapi 5 aplana el JSON por defecto; el parser de catálogo espera el formato v4 (data[].attributes).
        builder.defaultHeader("Strapi-Response-Format", "v4");
        final String token = properties.getApiToken();
        if (token != null && !token.isBlank()) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.trim());
        }
        return builder.build();
    }

    private static String trimTrailingSlash(final String url) {
        if (url == null || url.isBlank()) {
            return "http://localhost:1337";
        }
        String u = url.trim();
        while (u.endsWith("/")) {
            u = u.substring(0, u.length() - 1);
        }
        return u;
    }
}
