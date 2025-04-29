package com.ahusain.journalapp.securtity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JWTService {

    private String encodedKey = "";

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSha256");
            SecretKey secretKey = keyGenerator.generateKey();
            encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            log.error("An error occurred while generating key", e);
        }
    }

    public String getToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims().add(claims)
                .subject(username).
                issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .and()
                .signWith(getSignKey()).header().add("typ", "JWT").and().compact();
    }

    public SecretKey getSignKey() {
        byte[] decode = Decoders.BASE64.decode(encodedKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        return getAllClaims(token).getSubject();
    }


//    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = getAllClaims(token);
//        return claimsResolver.apply(claims);
//    }


    public Claims getAllClaims(String token) {
        return Jwts.parser().
                verifyWith(getSignKey()).build().
                parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return getExpiryDate(token).before(new Date(System.currentTimeMillis()));
    }

    public Date getExpiryDate(String token) {
        return getAllClaims(token).getExpiration();
    }
}
