package com.sandbox.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class LogOutCacheConfiguration {

    private final LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {

                @Override
                public String load(String key) {
                    return key;
                }
            });

    public String get(String key) {
        return loadingCache.getIfPresent(key);
    }

    public void add(String key, String value) {
        if (key != null && key.startsWith("Bearer ") && value != null) {
            loadingCache.put(key.substring(7), value);
        }
    }
}
