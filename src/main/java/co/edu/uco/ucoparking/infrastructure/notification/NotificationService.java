package co.edu.uco.ucoparking.infrastructure.notification;

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

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    private final SendGrid sendGrid;
    private final String fromEmail;
    private final String fromName;
    private final boolean sendGridEnabled;

    public NotificationService(
            @Value("${sendgrid.api-key:}") final String apiKey,
            @Value("${sendgrid.from-email:}") final String fromEmail,
            @Value("${sendgrid.from-name:UcoParking}") final String fromName,
            @Value("${sendgrid.enabled:true}") final boolean sendGridEnabled) {
        this.sendGridEnabled = sendGridEnabled && apiKey != null && !apiKey.isBlank();
        this.sendGrid = this.sendGridEnabled ? new SendGrid(apiKey) : null;
        this.fromEmail = fromEmail != null ? fromEmail.trim() : "";
        this.fromName = fromName != null ? fromName : "UcoParking";
    }

    /**
     * Envía confirmación de reserva por SendGrid. No lanza excepción: errores solo se registran.
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

        final Email from = new Email(fromEmail, fromName);
        final Email to = new Email(toEmail);
        final String subject = "Reserva de parqueadero confirmada - UcoParking";
        final String safeName = studentName != null ? studentName : "estudiante";
        final String body =
                "<h2>¡Hola, " + escapeHtml(safeName) + "!</h2>"
                        + "<p>Tu reserva en el cupo <strong>" + escapeHtml(spotCode) + "</strong> ha sido confirmada.</p>"
                        + "<ul>"
                        + "<li><strong>Placa:</strong> " + escapeHtml(plate != null ? plate : "") + "</li>"
                        + "<li><strong>Franja (America/Bogotá):</strong> "
                        + escapeHtml(startTime != null ? startTime : "")
                        + " – "
                        + escapeHtml(endTime != null ? endTime : "")
                        + "</li>"
                        + "</ul>"
                        + "<p>Recuerda llegar a tiempo y respetar el horario.</p>"
                        + "<p>— Equipo UcoParking · Universidad Católica de Oriente</p>";

        final Content content = new Content("text/html", body);
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
