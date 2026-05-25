package co.edu.uco.ucoparking.infrastructure.security;

import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

/**
 * Cuando el issuer de Auth0 está configurado ({@link Auth0RuntimeSettings}), exige que el JWT
 * (claim {@code email}) coincida con el recurso solicitado (alineado con el SPA).
 */
@Component
public class Auth0ApiAuthorization {

    private final Auth0RuntimeSettings auth0RuntimeSettings;
    private final StudentRepository studentRepository;

    public Auth0ApiAuthorization(
            final Auth0RuntimeSettings auth0RuntimeSettings,
            final StudentRepository studentRepository) {
        this.auth0RuntimeSettings = auth0RuntimeSettings;
        this.studentRepository = studentRepository;
    }

    public boolean isAuth0SecurityEnabled() {
        return auth0RuntimeSettings.isJwtSecurityEnabled();
    }

    public void assertLookupEmailAllowed(final String email, final Authentication authentication) {
        if (!isAuth0SecurityEnabled()) {
            return;
        }
        assertEmailMatchesToken(email, requireJwtAuthentication(authentication));
    }

    public void assertRegisterEmailAllowed(final String email, final Authentication authentication) {
        if (!isAuth0SecurityEnabled()) {
            return;
        }
        assertEmailMatchesToken(email, requireJwtAuthentication(authentication));
    }

    public void assertForStudentIdAllowed(final UUID forStudentId, final Authentication authentication) {
        if (!isAuth0SecurityEnabled() || forStudentId == null) {
            return;
        }
        assertStudentIdAllowed(forStudentId, authentication);
    }

    public void assertStudentIdAllowed(final UUID studentId, final Authentication authentication) {
        if (!isAuth0SecurityEnabled()) {
            return;
        }
        final Jwt jwt = requireJwtAuthentication(authentication);
        final StudentEntity student = studentRepository.findById(studentId);
        if (student == null) {
            throw UcoParkingException.of(MessagesEnum.PARKING_STUDENT_NOT_FOUND);
        }
        assertEmailMatchesToken(student.geteMail(), jwt);
    }

    private Jwt requireJwtAuthentication(final Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken();
        }
        throw UcoParkingException.of(MessagesEnum.COMMON_UNAUTHORIZED_API);
    }

    private void assertEmailMatchesToken(final String email, final Jwt jwt) {
        final String tokenEmail = extractEmail(jwt);
        if (tokenEmail == null || tokenEmail.isBlank()) {
            throw UcoParkingException.of(MessagesEnum.AUTH0_TOKEN_EMAIL_MISSING);
        }
        if (!normalizeEmail(tokenEmail).equals(normalizeEmail(email))) {
            throw UcoParkingException.of(MessagesEnum.AUTH0_EMAIL_MISMATCH);
        }
    }

    private static String normalizeEmail(final String raw) {
        return raw == null ? "" : raw.trim().toLowerCase(Locale.ROOT);
    }

    private static String extractEmail(final Jwt jwt) {
        final String email = jwt.getClaimAsString("email");
        if (email != null && !email.isBlank()) {
            return email;
        }
        return jwt.getClaimAsString("https://ucoparking/email");
    }
}
