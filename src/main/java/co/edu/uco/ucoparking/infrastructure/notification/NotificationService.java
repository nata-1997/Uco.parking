package co.edu.uco.ucoparking.infrastructure.notification;

import co.edu.uco.ucoparking.infrastructure.strapi.StrapiCatalogService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    private final SendGrid sendGrid;
    private final String fromEmail;
    private final String fromName;
    private final boolean sendGridEnabled;
    private final StrapiCatalogService strapiCatalogService;

    public NotificationService(
            @Value("${sendgrid.api-key:}") final String apiKey,
            @Value("${sendgrid.from-email:}") final String fromEmail,
            @Value("${sendgrid.from-name:UcoParking}") final String fromName,
            @Value("${sendgrid.enabled:true}") final boolean sendGridEnabled,
            final StrapiCatalogService strapiCatalogService) {
        this.sendGridEnabled = sendGridEnabled && apiKey != null && !apiKey.isBlank();
        this.sendGrid = this.sendGridEnabled ? new SendGrid(apiKey) : null;
        this.fromEmail = fromEmail != null ? fromEmail.trim() : "";
        this.fromName = fromName != null ? fromName : "UcoParking";
        this.strapiCatalogService = strapiCatalogService;
    }

    /**
     * Envía confirmación de reserva por SendGrid. No lanza excepción: errores solo se registran.
     * Asunto y cuerpo HTML provienen de Strapi (claves {@code email.reserva.asunto} y {@code email.reserva.cuerpoHtml}).
     */
    public void sendParkingReservationEmail(
            final String toEmail,
            final String studentName,
            final String spotCode,
            final String plate,
            final String startTime,
            final String endTime) {
        if (!sendGridEnabled || sendGrid == null) {
            LOG.debug("SendGrid deshabilitado o sin API key; no se envía correo de reserva.");
            return;
        }
        if (fromEmail.isBlank()) {
            LOG.warn("sendgrid.from-email vacío; no se puede enviar correo.");
            return;
        }

        final String subject = strapiCatalogService.getUiText("email.reserva.asunto", "").trim();
        if (subject.isBlank()) {
            LOG.warn("Strapi no define email.reserva.asunto; no se envía correo de reserva.");
            return;
        }

        final String safeName = studentName != null ? studentName : "";
        final Map<String, String> vars = new LinkedHashMap<>();
        vars.put("nombreEstudiante", escapeHtml(safeName));
        vars.put("cupo", escapeHtml(spotCode != null ? spotCode : ""));
        vars.put("placa", escapeHtml(plate != null ? plate : ""));
        vars.put("horaInicio", escapeHtml(startTime != null ? startTime : ""));
        vars.put("horaFin", escapeHtml(endTime != null ? endTime : ""));

        final String bodyHtml = strapiCatalogService.expandUiTemplate("email.reserva.cuerpoHtml", vars).trim();
        if (bodyHtml.isBlank()) {
            LOG.warn("Strapi no define email.reserva.cuerpoHtml; no se envía correo de reserva.");
            return;
        }

        final Email from = new Email(fromEmail, fromName);
        final Email to = new Email(toEmail);
        final Content content = new Content("text/html", bodyHtml);
        final Mail mail = new Mail(from, subject, to, content);

        final Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            final Response response = sendGrid.api(request);
            final int code = response.getStatusCode();
            if (code >= 200 && code < 300) {
                LOG.info("Correo de reserva enviado a {} (SendGrid HTTP {}).", toEmail, code);
            } else {
                LOG.warn(
                        "SendGrid respondió HTTP {} al enviar a {}: {}",
                        code,
                        toEmail,
                        response.getBody());
            }
        } catch (IOException e) {
            LOG.error("Error de red al enviar correo con SendGrid a {}: {}", toEmail, e.getMessage());
        }
    }

    private static String escapeHtml(final String raw) {
        if (raw == null) {
            return "";
        }
        return raw
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
