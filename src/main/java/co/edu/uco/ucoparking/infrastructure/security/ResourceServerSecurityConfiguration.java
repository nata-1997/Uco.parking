package co.edu.uco.ucoparking.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Resource server JWT (Auth0) sobre el stack <strong>servlet</strong> (Tomcat + MVC).
 * Si {@code spring.security.oauth2.resourceserver.jwt.issuer-uri} está vacío, el API queda abierto.
 */
@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(
			final HttpSecurity http,
			@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}") final String issuerUri) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);

		if (issuerUri == null || issuerUri.isBlank()) {
			return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).build();
		}

		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/api/v1/**").authenticated()
						.anyRequest().permitAll())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.build();
	}
}
