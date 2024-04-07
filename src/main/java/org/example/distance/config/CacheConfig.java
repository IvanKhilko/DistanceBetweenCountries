package org.example.distance.config;

import org.example.distance.cache.LRUCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig<K, V> {
    @Bean
    public LRUCache<K, V> lruCache() {
        return new LRUCache<>();
    }
}