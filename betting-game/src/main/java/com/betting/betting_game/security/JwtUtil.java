package com.betting.betting_game.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("$(jwt.secret)")
    private String secretKey;

    //token gecerli mi kontrol edilir
    public boolean isValidToken(String token) {
        try {
            //token doğrulama
            Jwts.parser()
                    .setSigningKey(secretKey)//secret key kullanım yeri
                    .parseClaimsJws(token);//token parse edilir,
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //token'dan kullanıcı bilgisi alma
    public String extractMail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
