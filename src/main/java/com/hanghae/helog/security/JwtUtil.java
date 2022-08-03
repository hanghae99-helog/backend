package com.hanghae.helog.security;

import com.hanghae.helog.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {
    private static final long EXP_TIME = 1000 * 60 * 60 * 10;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.util.secret}")
    private String SECRET;

    private Key getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private String createToken(Claims claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .signWith(getSecretKey(), SIGNATURE_ALGORITHM)
                .compact();
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());

        return createToken(claims, user.getId());
    }

    private Claims getAllClaims(String token) {
        log.info("getAllClaims token = {}", token);

        return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String getUserIdFromToken(String token) {
        String userId = String.valueOf(getAllClaims(token).get("userId"));

        log.info("getUsernameFromToken subject = {}", userId);

        return userId;
    }

    private Date getExpirationDate(String token) {
        Claims claims = getAllClaims(token);

        return claims.getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public Boolean isValidToken(String token, User user) {
        log.info("isValidToken token = {}", token);

        String username = getUserIdFromToken(token);

        return username.equals(user.getId()) && !isTokenExpired(token);
    }
}
