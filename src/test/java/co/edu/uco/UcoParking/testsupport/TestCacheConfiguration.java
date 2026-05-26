package co.edu.uco.UcoParking.testsupport;

import co.edu.uco.ucoparking.infrastructure.cache.CacheNames;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Sustituye Redis en pruebas: {@link co.edu.uco.ucoparking.infrastructure.config.CacheConfig} está desactivado con perfil {@code test}.
 */
@TestConfiguration
@EnableCaching
@Profile("test")
public class TestCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CacheNames.ACADEMIC_PROGRAMS, CacheNames.ID_TYPES);
    }
}
