package com.kurator.zuul.service;

import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static String SIGNINGKEY;

//    private static String PREFIX;
//
//
//    @Value("${PREFIX}")
//    public void setPREFIX(String prefix)
//    {
//        PREFIX = prefix ;
//    }

    @Value("${SIGNINGKEY}")
    public void setSIGNINGKEY(String signingkey) {
        SIGNINGKEY = signingkey;
    }


    // Authentication service for validating the jwt token
    public static Authentication getAuthentication(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JWT-TOKEN")) {
                    String user = null;
                    try {
                        user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(cookie.getValue()).getBody()
                                .get("un", String.class);
                    } catch (ExpiredJwtException exception){
                        return null;
                    }
                    if (user != null) {
                        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    }
                }
            }
        }

        return null;
    }
}
