package wushu.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token.ttl-millis}")
    private long accessTokenTtlMillis;

    @Value("${jwt.refresh-token.ttl-millis}")
    private long refreshTokenTtlMillis;

    private Key key;

    @PostConstruct
    public void setUpKey(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(String username, long ttlMillis, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ ttlMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(UserDetails user) {
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return generateToken(user.getUsername(), accessTokenTtlMillis, Map.of("roles", roles));
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(user.getUsername(), refreshTokenTtlMillis, Map.of());
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractFromToken(token, Claims::getExpiration).before(new Date());

        }catch (ExpiredJwtException e){
            log.warn("JWT token expired");
            return true;

        }catch (Exception e){
            log.warn("Error cheking token expiration", e.getMessage());
            return true;
        }
    }
    public String extractUsername(String token) {
        try {
            return extractFromToken(token, Claims::getSubject);
        }catch (Exception e){
            log.warn("Error extracting username", e.getMessage());
            return null;
        }
    }
    public <T> T extractFromToken(String token, Function<Claims, T> extractor) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return extractor.apply(claims);

        }catch (Exception e){
            log.warn("Error parsing token", e.getMessage());
            return null;
        }
    }
}
