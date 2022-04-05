package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IgniteService {
    private final Ignite igniteInstances;

    public boolean deleteCache(String cacheName) {
        igniteInstances.destroyCache(cacheName);
        return true;
    }

    public <T> CacheConfiguration<String, T> createCache(String cacheName, Class<T> tClass) {
        CacheConfiguration<String, T> cacheConfiguration = new CacheConfiguration<String, T>(cacheName)
                .setCacheMode(CacheMode.PARTITIONED)
                .setAtomicityMode(CacheAtomicityMode.ATOMIC)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, tClass);
        igniteInstances.getOrCreateCache(cacheConfiguration);
        return cacheConfiguration;
    }
}
