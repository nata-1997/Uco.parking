package co.edu.uco.ucoparking.infrastructure.config;

import co.edu.uco.ucoparking.infrastructure.cache.CacheNames;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
@org.springframework.context.annotation.Profile("!test")
public class CacheConfig {

    private static final Duration DEFAULT_TTL = Duration.ofMinutes(10);
    private static final Duration CATALOG_TTL = Duration.ofHours(1);

    /**
     * ObjectMapper dedicado a Redis (no el de HTTP) para no acoplar serialización de caché al API.
     */
    @Bean(name = "redisCacheObjectMapper")
    public ObjectMapper redisCacheObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    public RedisCacheManager cacheManager(
            final RedisConnectionFactory connectionFactory,
            @Qualifier("redisCacheObjectMapper") final ObjectMapper redisCacheObjectMapper) {

        final GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(redisCacheObjectMapper);
        final RedisSerializationContext.SerializationPair<Object> pair =
                RedisSerializationContext.SerializationPair.fromSerializer(serializer);

        final RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(DEFAULT_TTL)
                .serializeValuesWith(pair);

        final RedisCacheConfiguration catalogConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(CATALOG_TTL)
                .serializeValuesWith(pair);

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaults)
                .withCacheConfiguration(CacheNames.ID_TYPES, catalogConfig)
                .withCacheConfiguration(CacheNames.ACADEMIC_PROGRAMS, catalogConfig)
                .build();
    }
}