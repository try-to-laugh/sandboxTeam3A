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

    public boolean isJwtNotBanned(String key) {
        return loadingCache.getIfPresent(key) == null;
    }

    public void banJwt(String key) {
        if (key != null && key.startsWith("Bearer ")) {
            loadingCache.put(key.substring(7), key.substring(7));
        }
    }
}
