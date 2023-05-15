package com.example.eHospital.utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    public static String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isEmpty()) {
            throw new IllegalArgumentException("Authorization header is missing");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }

        String jwtToken = authHeader.substring(7); // Remove the "Bearer " prefix
        return jwtToken;
    }

    public static Claims parseJwtToken(String jwtToken) {
        String secretKey = "mysecretkeywhichmustnotbelessthan256bitslong";
        Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        // Parse the JWT token and retrieve the claims
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();
        Claims claims = jwtParser.parseClaimsJws(jwtToken).getBody();

        return claims;
    }
}
