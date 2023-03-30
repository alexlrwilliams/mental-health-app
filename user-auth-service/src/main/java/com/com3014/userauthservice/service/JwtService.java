package com.com3014.userauthservice.service;

import com.com3014.userauthservice.exceptions.InvalidTokenException;
import com.com3014.userauthservice.model.TokenType;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
    private final Key secretKey;

    private static final long HOUR = 1000 * 60 * 60;

    public static final String TOKEN_TYPE_CLAIM_KEY = "token_type";

    public static final String TOKEN_ID_KEY = "jti";

    @Value("${access.token.expiration}")
    private int accessTokenExpiration;

    @Value("${refresh.token.expiration}")
    private int refreshTokenExpiration;

    private HttpServletRequest request;

    private final RedisTokenRepository redisTokenRepository;

    @Autowired
    public JwtService(@Value("${jwt.secret}") String secret,
                      HttpServletRequest request,
                      RedisTokenRepository redisTokenRepository) {
        this.request = request;
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
        this.redisTokenRepository = redisTokenRepository;
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails, TokenType.ACCESS_TOKEN);
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails, TokenType.REFRESH_TOKEN);
    }

    public JsonTokenResponse generateTokenResponse(UserDetails userDetails) {
        var tokenId = UUID.randomUUID();
        return new JsonTokenResponse(
                generateAccessToken(userDetails, tokenId),
                generateRefreshToken(userDetails, tokenId)
        );
    }

    public String generateAccessToken(UserDetails userDetails, UUID tokenId) {
        return generateToken(tokenId, userDetails, TokenType.ACCESS_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + HOUR * accessTokenExpiration))
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails, UUID tokenId) {
        return generateToken(tokenId, userDetails, TokenType.REFRESH_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + HOUR * refreshTokenExpiration))
                .compact();
    }

    public JsonTokenResponse refreshAccessToken(String refreshToken, UserDetails userDetails) {
        if (validateRefreshToken(refreshToken, userDetails)) {
            return generateTokenResponse(userDetails);
        }
        throw new InvalidTokenException(
                "Refresh token invalid for user %s".formatted(userDetails.getUsername())
        );
    }

    private boolean validateToken(String token, UserDetails userDetails, TokenType expectedToken) {
        String username = extractUsername(token);
        String tokenType = extractClaim(token, claims ->
                claims.get(TOKEN_TYPE_CLAIM_KEY, String.class));
        String tokenId = extractClaim(
                token,
                claims -> (String) claims.get("jti")
        );

        return (TokenType.valueOf(tokenType).equals(expectedToken) &&
                username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token) &&
                redisTokenRepository.findById(tokenId).isEmpty());
    }

    private JwtBuilder generateToken(UUID tokenId, UserDetails userDetails, TokenType token_type) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(TOKEN_TYPE_CLAIM_KEY, token_type)
                .claim(TOKEN_ID_KEY, tokenId)
                .signWith(secretKey, SignatureAlgorithm.HS256);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }
}
