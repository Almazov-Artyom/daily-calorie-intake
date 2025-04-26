package ru.almaz.dailycalorieintake.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class JwtCacheService {
    @CachePut(value = "${cache.access-token.name}",key = "#username")
    public String putAccessToken(String accessToken, String username) {
        return accessToken;
    }
}