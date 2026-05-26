package co.edu.uco.ucoparking.infrastructure.strapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lee colecciones Strapi (mensajes de API y textos de UI) y mantiene caché en memoria.
 */
@Service
public class StrapiCatalogService {

    private static final Logger LOG = LoggerFactory.getLogger(StrapiCatalogService.class);

    private final RestClient strapiRestClient;
    private final StrapiProperties properties;
    private final ObjectMapper objectMapper;

    private final Map<String, ApiMessageRow> apiMessages = new ConcurrentHashMap<>();
    private final Map<String, String> uiMessages = new ConcurrentHashMap<>();

    public StrapiCatalogService(
            @Qualifier("strapiRestClient") final RestClient strapiRestClient,
            final StrapiProperties properties,
            final ObjectMapper objectMapper) {
        this.strapiRestClient = strapiRestClient;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadOnReady() {
        refreshOrFail("inicio");
    }

    /** Invocado solo cuando el intervalo {@code strapi.refresh-ms} es &gt; 0 (ver {@link StrapiSchedulingConfiguration}). */
    public void scheduledRefresh() {
        try {
            refreshOrFail("programado");
        } catch (Exception e) {
            LOG.warn("No se pudo refrescar catálogo Strapi ({}): {}", "programado", e.getMessage());
        }
    }

    public void refreshOrFail(final String motivo) {
        if (!properties.isRemoteEnabled()) {
            apiMessages.clear();
            uiMessages.clear();
            LOG.debug("Omitiendo Strapi (strapi.remote-enabled=false), catálogo vacío ({})", motivo);
            return;
        }
        try {
            final Map<String, ApiMessageRow> api = fetchApiMessages();
            final Map<String, String> ui = fetchUiMessages();
            if (api.isEmpty() && properties.isFailOnStartup()) {
                throw new IllegalStateException("Strapi devolvió 0 mensajes de API (colección "
                        + properties.getCollectionApiMessages() + ").");
            }
            apiMessages.clear();
            apiMessages.putAll(api);
            uiMessages.clear();
            uiMessages.putAll(ui);
            LOG.info("Catálogo Strapi cargado ({}) — API: {} entradas, UI: {} entradas.", motivo, api.size(), ui.size());
        } catch (Exception e) {
            if (properties.isFailOnStartup()) {
                LOG.error("Fallo al cargar Strapi ({}): {}", motivo, e.getMessage());
                throw new IllegalStateException("No se pudo cargar el catálogo desde Strapi. Revise STRAPI_URL y STRAPI_API_TOKEN.", e);
            }
            LOG.warn("Strapi no disponible ({}): {}", motivo, e.getMessage());
        }
    }

    public String getUiText(final String clave, final String valorSiFalta) {
        return uiMessages.getOrDefault(clave, valorSiFalta != null ? valorSiFalta : clave);
    }

    /**
     * Sustituye marcadores {@code {{nombre}}} en una plantilla cargada desde la colección UI (clave completa).
     */
    public String expandUiTemplate(final String clavePlantilla, final Map<String, String> marcadores) {
        String plantilla = uiMessages.getOrDefault(clavePlantilla, "");
        if (marcadores != null) {
            for (Map.Entry<String, String> e : marcadores.entrySet()) {
                final String token = "{{" + e.getKey() + "}}";
                plantilla = plantilla.replace(token, e.getValue() != null ? e.getValue() : "");
            }
        }
        return plantilla;
    }

    public Map<String, String> snapshotUi() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(uiMessages));
    }

    public ApiMessageRow getApiRow(final String codigo) {
        return apiMessages.get(codigo);
    }

    private Map<String, ApiMessageRow> fetchApiMessages() {
        final String path = "/api/" + properties.getCollectionApiMessages();
        final String json = getCollectionJson(path);
        return parseApiMessages(json);
    }

    private Map<String, String> fetchUiMessages() {
        final String path = "/api/" + properties.getCollectionUiMessages();
        final String json = getCollectionJson(path);
        return parseUiMessages(json);
    }

    private String getCollectionJson(final String path) {
        try {
            return strapiRestClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
                            .queryParam("pagination[pageSize]", "500")
                            .build())
                    .retrieve()
                    .body(String.class);
        } catch (RestClientException e) {
            throw new IllegalStateException("HTTP Strapi: " + e.getMessage(), e);
        }
    }

    private Map<String, ApiMessageRow> parseApiMessages(final String json) {
        try {
            final JsonNode root = objectMapper.readTree(json);
            final JsonNode data = root.path("data");
            if (!data.isArray()) {
                return Map.of();
            }
            final Map<String, ApiMessageRow> out = new LinkedHashMap<>();
            for (JsonNode item : data) {
                final JsonNode attrs = resolveAttributes(item);
                final String codigo = text(attrs, "codigo");
                if (codigo.isBlank()) {
                    continue;
                }
                final String usuario = text(attrs, "textoUsuario");
                final String tecnico = text(attrs, "textoTecnico");
                out.put(codigo, new ApiMessageRow(codigo, usuario, tecnico));
            }
            return out;
        } catch (java.io.IOException e) {
            throw new IllegalStateException("JSON inválido desde Strapi (mensajes API)", e);
        }
    }

    private Map<String, String> parseUiMessages(final String json) {
        try {
            final JsonNode root = objectMapper.readTree(json);
            final JsonNode data = root.path("data");
            if (!data.isArray()) {
                return Map.of();
            }
            final Map<String, String> out = new LinkedHashMap<>();
            for (JsonNode item : data) {
                final JsonNode attrs = resolveAttributes(item);
                final String clave = firstNonBlank(text(attrs, "clave"), text(attrs, "key"));
                final String valor = firstNonBlank(text(attrs, "valor"), text(attrs, "value"));
                if (!clave.isBlank()) {
                    out.put(clave, valor);
                }
            }
            return out;
        } catch (java.io.IOException e) {
            throw new IllegalStateException("JSON inválido desde Strapi (mensajes UI)", e);
        }
    }

    private static JsonNode resolveAttributes(final JsonNode item) {
        if (item.hasNonNull("attributes") && item.get("attributes").isObject()) {
            return item.get("attributes");
        }
        return item;
    }

    private static String text(final JsonNode attrs, final String field) {
        if (attrs == null || !attrs.has(field) || attrs.get(field).isNull()) {
            return "";
        }
        final JsonNode n = attrs.get(field);
        if (n.isTextual()) {
            return n.asText("");
        }
        if (n.isValueNode()) {
            return n.asText("");
        }
        return n.toString();
    }

    private static String firstNonBlank(final String a, final String b) {
        if (a != null && !a.isBlank()) {
            return a;
        }
        return b != null ? b : "";
    }

    public record ApiMessageRow(String codigo, String textoUsuario, String textoTecnico) {
    }
}
