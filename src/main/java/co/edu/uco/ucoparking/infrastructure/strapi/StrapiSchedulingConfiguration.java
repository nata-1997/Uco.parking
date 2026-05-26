package co.edu.uco.ucoparking.infrastructure.strapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Programa el refresco periódico de Strapi solo si {@code strapi.refresh-ms} &gt; 0.
 * Un valor 0 (p. ej. {@code STRAPI_REFRESH_MS=0}) desactiva el scheduler y evita fallos al arrancar tests.
 */
@Configuration
@EnableScheduling
public class StrapiSchedulingConfiguration implements SchedulingConfigurer {

    private final StrapiCatalogService strapiCatalogService;
    private final StrapiProperties strapiProperties;

    public StrapiSchedulingConfiguration(
            final StrapiCatalogService strapiCatalogService,
            final StrapiProperties strapiProperties) {
        this.strapiCatalogService = strapiCatalogService;
        this.strapiProperties = strapiProperties;
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        final long ms = strapiProperties.getRefreshMs();
        if (strapiProperties.isRemoteEnabled() && ms > 0) {
            taskRegistrar.addFixedDelayTask(strapiCatalogService::scheduledRefresh, ms);
        }
    }
}
