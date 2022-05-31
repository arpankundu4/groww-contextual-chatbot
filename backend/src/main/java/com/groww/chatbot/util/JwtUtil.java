package com.groww.chatbot.util;

import com.groww.chatbot.dto.GrowwUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * jwt utility methods class
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration.time}")
    private long EXPIRATION_TIME;

    // generates token using user details
    public String generateToken(GrowwUserDetails growwUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, growwUserDetails.getEmail());
    }

    // creates token using Jwts API
    // setting all the required parameters
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                             .setSubject(subject)
                             .setIssuedAt(new Date(System.currentTimeMillis()))
                             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                             .signWith(SignatureAlgorithm.HS256, SECRET)
                             .compact();
    }

    // validates token
    public Boolean validateToken(String token, GrowwUserDetails growwUserDetails) {
        final String email = extractEmail(token);
        return email.equals(growwUserDetails.getEmail()) && !isTokenExpired(token);
    }

    // checks if token expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // extracts email from token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // extracts expiration time from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // extracts claim
    // return type as per type argument
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extracts all claims from token
    private Claims extractAllClaims(String token) throws RuntimeException {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    // extracts email from authorization header
    public String extractEmailFromAuthHeader(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return extractEmail(token);
    }

}
