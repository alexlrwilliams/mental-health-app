package com.com3014.userauthservice.service;

import com.com3014.userauthservice.JwtTestHelper;
import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.model.BlacklistedToken;
import com.com3014.userauthservice.model.TokenType;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private RedisTokenRepository redisTokenRepository;

    private SecretKey secretKey;

    private final User user = UnitTestHelper.testUser1;
    private final User user2 = UnitTestHelper.testUser2;

    private final String accessToken = JwtTestHelper.jwtToken(user, "id", TokenType.ACCESS_TOKEN);
    private final String refreshToken = JwtTestHelper.jwtToken(user, "id", TokenType.REFRESH_TOKEN);

    JwtServiceTest() throws ParseException {
    }

    @BeforeEach
    void setup() {
        jwtService = new JwtService(JwtTestHelper.testSecret, 24, 720, redisTokenRepository);
        secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(JwtTestHelper.testSecret)
        );
    }

    @Test
    void extractUsername() {
        assertThat(jwtService.extractUsername(accessToken)).isEqualTo(user.getUsername());
    }

    @Test
    void extractExpiration() {
        assertThat(jwtService.extractExpiration(accessToken)).isEqualTo(JwtTestHelper.EXPIRATION_DATE);
    }

    @Test
    void validateAccessToken() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.empty());
        assertThat(jwtService.validateAccessToken(accessToken, user)).isTrue();
    }

    @Test
    void validateAccessToken__invalid_token_type() {
        assertThat(jwtService.validateAccessToken(refreshToken, user)).isFalse();
    }

    @Test
    void validateAccessToken__invalid_user() {
        assertThat(jwtService.validateAccessToken(accessToken, user2)).isFalse();
    }

    @Test
    void validateAccessToken__token_blacklisted() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.of(new BlacklistedToken("id")));
        assertThat(jwtService.validateAccessToken(accessToken, user)).isFalse();
    }

    @Test
    void validateRefreshToken() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.empty());
        assertThat(jwtService.validateRefreshToken(refreshToken, user)).isTrue();
    }

    @Test
    void validateRefreshToken__invalid_token_type() {
        assertThat(jwtService.validateRefreshToken(accessToken, user)).isFalse();
    }

    @Test
    void validateRefreshToken__invalid_user() {
        assertThat(jwtService.validateRefreshToken(refreshToken, user2)).isFalse();
    }

    @Test
    void validateRefreshToken__token_blacklisted() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.of(new BlacklistedToken("id")));
        assertThat(jwtService.validateRefreshToken(refreshToken, user)).isFalse();
    }

    @Test
    void generateAccessToken() {
        var tokenId = UUID.randomUUID();
        var token = jwtService.generateAccessToken(user, tokenId);
        var test = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        assertThat(test)
                .extracting("sub", "token_type", "jti")
                .containsExactly(user.getUsername(), TokenType.ACCESS_TOKEN.name(), tokenId.toString());

        assertThat(test.get("iat", Date.class))
                .isToday();

        assertThat(test.get("iat", Date.class).toInstant())
                .isEqualTo(test.get("exp", Date.class).toInstant().minus(24, ChronoUnit.HOURS));
    }

    @Test
    void generateRefreshToken() {
        var tokenId = UUID.randomUUID();
        var token = jwtService.generateRefreshToken(user, tokenId);
        var tokenClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        assertThat(tokenClaims)
                .extracting("sub", "token_type", "jti")
                .containsExactly(user.getUsername(), TokenType.REFRESH_TOKEN.name(), tokenId.toString());

        assertThat(tokenClaims.get("iat", Date.class))
                .isToday();

        assertThat(tokenClaims.get("iat", Date.class).toInstant())
                .isEqualTo(tokenClaims.get("exp", Date.class).toInstant().minus(720, ChronoUnit.HOURS));
    }

    @Test
    void refreshAccessToken() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.empty());
        assertThat(jwtService.refreshAccessToken(refreshToken, user))
                .isInstanceOf(JsonTokenResponse.class);
    }

    @Test
    void refreshAccessToken__invalid_refresh_token() {
        when(redisTokenRepository.findById("id")).thenReturn(Optional.of(new BlacklistedToken("id")));
        assertThatThrownBy(() -> jwtService.refreshAccessToken(refreshToken, user));
    }
}