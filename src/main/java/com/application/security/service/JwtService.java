package com.application.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtService {
    @Value("${security.secret}")
    String secretKey;
    @Value("${security.access-token.expiration}")
    long accessExpiration;
    @Value("${security.refresh-token.expiration}")
    long refreshExpiration;

    public String generateAccessToken(UserDetails user, String ...args) {
        return buildToken(Map.of("token_id", args[0], "role", args[1]), user, accessExpiration);
    }

    public String generateRefreshToken(UserDetails user) {
        return buildToken(new HashMap<>(), user, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails user,
                              long expiration) {
        Date currentTime = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public UUID extractTokenId(String token) {
        return UUID.fromString(extractClaims(token,
            (claims) -> claims.get("token_id", String.class)));
    }

    public String extractRole(String token) {
        return extractClaims(token,
                (claims) -> claims.get("role", String.class));
    }

    public Date extractExpirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder().setSigningKey(generateKey()).build().parse(token);
        } catch (Exception e) {
            return false;
        }
        return isTokenNonExpired(token);
    }

    private boolean isTokenNonExpired(String token) {
        Date date = extractClaims(token, Claims::getExpiration);
        return date.after(new Date());
    }

    private  <T>T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims;
        try {
            claims = extractAllClaims(token);
        } catch (ExpiredJwtException e){
            claims = e.getClaims();
        }
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
