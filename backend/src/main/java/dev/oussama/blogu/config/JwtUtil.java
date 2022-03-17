package dev.oussama.blogu.config;

import dev.oussama.blogu.config.ArtSoukUtils.JwtConstants;
import dev.oussama.blogu.services.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String secret;
    private final JwtUserDetailsService jwtService;

    public JwtUtil(JwtUserDetailsService jwtService, @Value("${server.jwt.secret}") String secret) {
        this.jwtService = jwtService;
        this.secret = secret;
    }

    public UserDetails translateUserFromToken(final String token) throws UsernameNotFoundException {
        return jwtService.loadUserByUsername(getUsernameFromToken(token));
    }

    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaimsFromToken(removeJsonBrackets(token)));
    }

    public String createTokenFromUserDetails(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", new ArrayList<>(userDetails.getAuthorities()).get(0).getAuthority());
        return generateToken(claims, userDetails.getUsername());
    }

    public boolean isTokenValid(final String token, final String username) {
        return isUsernameInToken(token, username) && !isTokenExpired(token);
    }

    private Claims getClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(removeJsonBrackets(token)).getBody();
    }

    private String removeJsonBrackets(final String token) {
        return token.replace("{", "").replace("}", "");
    }

    private boolean isTokenExpired(final String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date()).setExpiration(getExpirationDateForToken())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)).compact();
    }

    private Date getExpirationDateForToken() {
        return new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME);
    }

    private boolean isUsernameInToken(final String token, final String username) {
        return getUsernameFromToken(token).equals(username);
    }
}