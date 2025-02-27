package com.example.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public static final String AUTHORITIES_KEY = "privileges";

    private final ObjectMapper objectMapper;

    public TokenProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertToPrivilegeStr(List<String> privileges) {
        if (privileges == null)
            return null;
        return String.join(",", privileges);
    }

    public String generateAccessToken(
            Long userId,
            String userName,
            String displayName,
            String roles) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.expiration * 3600 * 1000);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", userId);
        payload.put("display_name", displayName);
        payload.put("user_name", userName);
        payload.put(AUTHORITIES_KEY, roles);

        String payloadData;

        try {
            payloadData = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder()
                .setSubject(userName)
                .claim("data", payloadData)
                .claim(AUTHORITIES_KEY, roles)
                .setId(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return Optional.of(jws);
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex);
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or only whitespace", ex);
        } catch (MalformedJwtException ex) {
            log.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            log.error("Signature validation failed");
        }
        return Optional.empty();
    }
}
