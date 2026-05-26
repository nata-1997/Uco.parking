package co.edu.uco.ucoparking.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Auth0RuntimeSettings auth0RuntimeSettings;

    @Value("${uco.auth0.audience:}")
    private String audience;

    public SecurityConfig(final Auth0RuntimeSettings auth0RuntimeSettings) {
        this.auth0RuntimeSettings = auth0RuntimeSettings;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http) throws Exception {
        http.securityMatcher("/api/v1/**")
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());

        if (!auth0RuntimeSettings.isJwtSecurityEnabled()) {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/catalogo/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private NimbusJwtDecoder jwtDecoder() {
        final String issuer = auth0RuntimeSettings.getIssuerLocation().trim();
        // Discovery OIDC (/.well-known/openid-configuration): timeouts explícitos para redes lentas o proxy.
        final SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(45_000);
        final RestTemplate oidcRestTemplate = new RestTemplate(factory);
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withIssuerLocation(issuer)
                .restOperations(oidcRestTemplate)
                .build();
        final OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        if (audience == null || audience.isBlank()) {
            decoder.setJwtValidator(withIssuer);
            return decoder;
        }
        final String expectedAud = audience.trim();
        final OAuth2TokenValidator<Jwt> audienceValidator = jwt -> {
            final Object aud = jwt.getClaim("aud");
            if (aud instanceof String s && expectedAud.equals(s)) {
                return OAuth2TokenValidatorResult.success();
            }
            if (aud instanceof Collection<?> c) {
                for (final Object o : c) {
                    if (expectedAud.equals(String.valueOf(o))) {
                        return OAuth2TokenValidatorResult.success();
                    }
                }
            }
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "El token no incluye la audiencia (aud) esperada.", null));
        };
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator));
        return decoder;
    }
}
