package io.soffa.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.soffa.core.exception.TechnicalException;
import io.soffa.core.commons.DateSupport;

import java.util.Date;
import java.util.Map;

public class JwtTokenProvider implements TokenProvider {

    public static final String DEFAULT_ISSUER = "app";
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private String issuer;

    public JwtTokenProvider(String secret, String issuer) {
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();
    }

    public JwtTokenProvider(String secret) {
        this(secret, DEFAULT_ISSUER);
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            return verifier.verify(token).getExpiresAt().before(new Date());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return true;
        }
    }

    @Override
    public Map<String, String> getAllClaimsFromToken(String token) {
        return null;
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            throw new TechnicalException("INVALID_JWT");
        }
    }

    public String generateToken(String subject, Map<String, String> claims) {
        return generateToken(subject, claims, 24);
    }

    @Override
    public String generateToken(String subject, Map<String, String> claims, int expirationInHours) {
        try {
            JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(DateSupport.plusHours(new Date(), expirationInHours))
                .withSubject(subject);
            claims.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TechnicalException(exception.getMessage());
        }
    }
}
