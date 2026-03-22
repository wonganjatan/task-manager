package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final Key key = Keys.hmacShaKeyFor(
            "jav_spring_boot_react_rest-api-123456789".getBytes());

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(key)
                .compact();
    }

    private Claims parseToken(String token) {
        try {
            token = token.replace("Bearer ", "");

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token is expired");
        } catch (JwtException e) {
            throw new RuntimeException("Token is invalid");
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    @Override
    public String getRoleFromToken(String token) {
        return parseToken(token).get("role").toString();
    }
}
