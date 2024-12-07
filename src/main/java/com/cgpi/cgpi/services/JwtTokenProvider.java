//package com.cgpi.cgpi.services;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class JwtTokenProvider {
//    private final String JWT_SECRET = "Omkar123"; // Use a secure key
//    private final long JWT_EXPIRATION = 604800000L; // 7 days in milliseconds
//
//    public String generateToken(Authentication authentication) {
//        String username = authentication.getName();
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//}
package com.cgpi.cgpi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JwtTokenProvider {
    private final String JWT_SECRET = "Omkar123";  // Secure key for signing the token
    private final long JWT_EXPIRATION = 604800000L;  // Token expiration time (7 days in milliseconds)

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return new UsernamePasswordAuthenticationToken(username, "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
    }
}
