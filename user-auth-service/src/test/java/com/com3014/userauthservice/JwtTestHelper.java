package com.com3014.userauthservice;

import com.com3014.userauthservice.model.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static com.com3014.userauthservice.service.JwtService.TOKEN_ID_KEY;
import static com.com3014.userauthservice.service.JwtService.TOKEN_TYPE_CLAIM_KEY;

public class JwtTestHelper {

    public static final String testSecret = "testSecret12342701021791208432703223188703223188";

    public static final Date EXPIRATION_DATE = Date.from(Instant.parse("3000-12-31T18:35:24.00Z"));


    public static String jwtToken(UserDetails userDetails, String tokenId, TokenType tokenType) throws ParseException {
        var secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(testSecret)
        );

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .claim(TOKEN_TYPE_CLAIM_KEY, tokenType)
                .claim(TOKEN_ID_KEY, tokenId)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(EXPIRATION_DATE)
                .compact();
    }
}
