package xyz.mariosm.status.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.signing.key}")
    protected String signingKey;

    @Value("${jwt.claims.issuer}")
    private String issuer;

    @Value("${jwt.claims.duration}")
    private String duration;

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claim) {
        return claim.apply(this.extractClaims(token));
    }

    @Override
    public String getUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + Long.parseLong(duration)))
                   .subject(userDetails.getUsername())
                   .issuer(issuer)
                   .signWith(this.getSecretKey()).compact();
    }

    @Override
    public boolean verifyToken(String token, UserDetails userDetails) {
        final String username = this.getUsername(token);
        final Date tokenIssued = this.extractClaim(token, Claims::getIssuedAt);
        final Date tokenExpiration = this.extractClaim(token, Claims::getExpiration);

        return userDetails.getUsername().equals(username)
               && tokenIssued.before(new Date())
               && tokenExpiration.after(new Date());
    }

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKey));
    }
}
