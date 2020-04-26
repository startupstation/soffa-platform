package io.soffa.platform.core.security;

import java.util.Map;

public interface TokenProvider {

    boolean isTokenExpired(String token);

    Map<String,String> getAllClaimsFromToken(String token);

    String getUsernameFromToken(String token);

    String generateToken(String username, Map<String, String> claims);

    String generateToken(String subject, Map<String, String> claims, int expirationInHours);

}
