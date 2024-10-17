package xyz.mariosm.status.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.function.Function;

public interface JwtService {
    Claims extractClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> claim);

    String getUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean verifyToken(String token, UserDetails userDetails);

    SecretKey getSecretKey();
}
