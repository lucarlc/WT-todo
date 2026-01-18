package htw.webtech.WT_todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationSeconds;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-seconds}") long expirationSeconds
    ) {
        this.signingKey = Keys.hmacShaKeyFor(normalizeSecret(secret));
        this.expirationSeconds = expirationSeconds;
    }

    public String createToken(String username) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = parse(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        try {
            String u = extractUsername(token);
            return u != null && u.equals(expectedUsername);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * HS256 benoetigt mindestens 256-bit Key (32 bytes). Wenn ein zu kurzer
     * Secret-String konfiguriert wurde, wird er fuer Demo-Zwecke aufgepolstert.
     */
    private byte[] normalizeSecret(String secret) {
        if (secret == null) secret = "";
        byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
        if (raw.length >= 32) return raw;

        byte[] out = new byte[32];
        for (int i = 0; i < out.length; i++) {
            out[i] = raw.length == 0 ? (byte) '0' : raw[i % raw.length];
        }
        return out;
    }
}
