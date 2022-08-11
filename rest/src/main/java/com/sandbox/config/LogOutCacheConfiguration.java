package com.sandbox.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sandbox.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class LogOutCacheConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    private final LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {

                @Override
                public String load(String key) {
                    return key;
                }
            });

    public boolean isJwtNotBanned(String token) {
        return loadingCache.getIfPresent(token) == null;
    }

    public void banJwt(String token) {
        String resolvedToken = jwtTokenProvider.resolveToken(token);
        loadingCache.put(resolvedToken, resolvedToken);
    }
}
