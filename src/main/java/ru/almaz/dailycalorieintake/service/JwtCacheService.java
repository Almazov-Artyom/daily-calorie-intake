package ru.almaz.dailycalorieintake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtCacheService {
    private final CacheManager cacheManager;

    @Value(value = "${cache.access-token.name}")
    private String accessTokenCacheName;

    @Value(value = "${cache.refresh-token.name}")
    private String refreshTokenCacheName;

    public void putAccessToken(String username, String accessToken) {
        Cache accessTokenCache = cacheManager.getCache(accessTokenCacheName);
        if (accessTokenCache != null) {
            accessTokenCache.put(username, accessToken);
        }
    }

    public void putRefreshToken(String username, String refreshToken) {
        Cache refreshTokenCache = cacheManager.getCache(refreshTokenCacheName);
        if (refreshTokenCache != null) {
            refreshTokenCache.put(username, refreshToken);
        }
    }

    public String getAccessToken(String username) {
        Cache accessTokenCache = cacheManager.getCache(accessTokenCacheName);
        String accessToken = null;
        if (accessTokenCache != null) {
            accessToken = accessTokenCache.get(username, String.class);
        }
        return accessToken;
    }
}