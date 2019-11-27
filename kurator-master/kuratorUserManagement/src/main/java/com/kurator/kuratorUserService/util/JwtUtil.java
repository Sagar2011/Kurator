package com.kurator.kuratorUserService.util;


import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import com.kurator.kuratorUserService.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    static final long EXPIRATIONTIME = 3600000; // 1 hour in milliseconds
    @Value("${SIGNINGKEY}")
    private String SIGNINGKEY;
    @Value("${PREFIX}")
    private String PREFIX;

    // Add token to Authorization header
    public String addToken(HttpServletResponse res, User user) {
        Claims claims = Jwts.claims();
        claims.put("un", user.getUserName()); // username
        claims.put("n", user.getFirstName()); // given name or name of user
        claims.put("avt", user.getAvatarURL()); // avatar url
        claims.put("role",user.getUserRole()); //user role in system
        String jwtToken = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY).compact();
        return jwtToken;
    }
}


