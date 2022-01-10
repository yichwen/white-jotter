package org.ursprung.wj.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtUtil {

    private static final String CLAIM_NAME_KEY = "name";
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
//    private static final String CLAIM_KEY_ORG_ID = "org_id";

//    @Value("${auth.jwt.issuer:etuition}")
    private String issuer = "white-jotter";

//    @Value("${auth.jwt.secret:etuition}")
    private String secret = "white-jotter";

//    @Value("${auth.jwt.audience:etuition}")
    private String audience = "white-jotter";

//    @Value("${auth.jwt.ttl-in-seconds:86400}")
    private long timeToLiveInSeconds = 86400;

//    private SecretKey secretKey;

    public String generateJWT(Map<String, Object> claims) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
//                .setSubject(claims.get("username").toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(timeToLiveInSeconds))))
                .addClaims(claims)
//                .claim(CLAIM_NAME_KEY, user.getUsername())
//                .claim(CLAIM_KEY_ORG_ID, orgId)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

}
