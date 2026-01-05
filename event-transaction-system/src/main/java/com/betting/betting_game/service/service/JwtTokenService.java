package com.betting.betting_game.service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret; // Token oluşturmak için kullanılan gizli anahtar

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs; // Token'ın geçerlilik süresi (ms)

    //JWT token oluşturma
    public String generateToken(String mail) {

        return Jwts.builder()
                .setSubject(mail)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    // Tokandan Claim bilgisi alma

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    //Token dan kullanıcı adını alma

    public String getUserMailFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    //Token geçerlimi kontrol etme 

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            //Token geçersiz veya süresi dolmuş olabilir
            return false;
        }
    }


}
