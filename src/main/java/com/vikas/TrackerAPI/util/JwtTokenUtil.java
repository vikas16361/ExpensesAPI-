package com.vikas.TrackerAPI.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtTokenUtil {
    private static final long JWT_TOKEN_VALIDITY =5*60*60 ;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();

        String token =Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        return token;
    }


    private Key getKey() {
        byte[] encodedKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    private <T> T getClaimfromToken(String token, Function<Claims,T> claimResolver) {
        final Claims claim = Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        return claimResolver.apply(claim);
    }

    public String getUsernameFromToken(String jwtToken) {
        return getClaimfromToken(jwtToken,Claims :: getSubject);
    }

    public Date getIssuedAtFromToken(String jwtToken) {
        return getClaimfromToken(jwtToken,Claims :: getIssuedAt);
    }
    public Date getExpirationFromToken(String jwtToken) {
        return getClaimfromToken(jwtToken,Claims :: getExpiration);
    }
    public String getIdFromToken(String jwtToken) {
        return getClaimfromToken(jwtToken,Claims :: getId);
    }


    public boolean validateToken(String jwtToken, UserDetails user) {
        final String username = getUsernameFromToken(jwtToken);
        return  username.equals(user.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        final Date expiration = getExpirationFromToken(jwtToken);
        return expiration.before(new Date());
    }
}
