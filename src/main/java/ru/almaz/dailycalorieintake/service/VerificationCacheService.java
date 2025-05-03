package ru.almaz.dailycalorieintake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.almaz.dailycalorieintake.entity.User;

@Service
@RequiredArgsConstructor
public class VerificationCacheService {
    private final CacheManager cacheManager;

    @Value("${cache.verification-cache.name}")
    private String cacheName;

    public void putUser(String key, User user) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, user);
        }
    }

    public User getUser(String key) {
        Cache cache = cacheManager.getCache(cacheName);
        User user = null;
        if (cache != null) {
            user = cache.get(key, User.class);
        }
        return user;
    }

    public void removeUser(String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
}
