package aigc.backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import aigc.backend.models.User;

@Service
public class JwtService {
    @Value("${security.jwt.secretkey}")
    private String secretKey;

    @Value("${security.jwt.expirationtime}")
    private long jwtExpiration;

    private static final String JWT_COOKIE_NAME = "jwtToken";

       public ResponseCookie[] generateJwtCookie(User userPrincipal) {
        String jwt = generateToken(new HashMap<>(), userPrincipal);
        ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, jwt)
                                              .path("/")
                                              .maxAge(7 * 24 * 60 * 60)
                                              .httpOnly(true)
                                              .secure(true)
                                              .sameSite("Strict")
                                              .build();

        String sessionId = UUID.randomUUID().toString();
        ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionId)
                                                .path("/")
                                                .maxAge(7 * 24 * 60 * 60)
                                                .httpOnly(false)
                                                .secure(true)
                                                .sameSite("Strict")
                                                .build();
        
        return new ResponseCookie[]{cookie, sessionCookie};

    }

    public ResponseCookie getCleanJwtCookie() {
        @SuppressWarnings("null")
        ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, null).path("/").build();
        return cookie;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        User user = (User) userDetails; 
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("userId", user.getId());
        System.out.println("claim: " + claims.toString());

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
  

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
