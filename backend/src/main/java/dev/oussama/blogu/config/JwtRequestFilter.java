package dev.oussama.blogu.config;

import dev.oussama.blogu.config.ArtSoukUtils.JwtConstants;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (isRouteRestricted(req.getRequestURI())) {
            final String requestTokenHeader = req.getHeader(ArtSoukUtils.JwtConstants.REQ_HEADER);
            String jwtToken = null;
            UserDetails user = null;

            log.info("Request token header: {}", SecurityContextHolder.getContext().getAuthentication());
            if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(JwtConstants.TOKEN_PREFIX.length());
                try {
                    log.warn("jwtToken : " + jwtToken);
                    user = jwtUtil.translateUserFromToken(jwtToken);
                } catch (JwtException e) {
                    log.warn("JWT exception raised", e);
                } catch (UsernameNotFoundException e) {
                    log.warn("username not found", e);
                }
            }
            if (requestTokenHeader == null) {
                log.warn("No JWT sent along the request");
            } else if (!requestTokenHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
                log.warn("JWT Token does not begin with Bearer String");
            }


            if (SecurityContextHolder.getContext().getAuthentication() == null && user != null && jwtUtil.isTokenValid(jwtToken, user.getUsername())) {
                log.warn("Authenticating user " + user.getUsername());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isRouteRestricted(String route) {
        String[] parts = route.split("/");
        route = String.format("/%s/%s", parts[parts.length - 2], parts[parts.length - 1]);
        log.info(route);
        for (String r : SecurityConfigProd.AUTH_WHITELIST)
            if (route.contains(r))
                return false;
        return true;
    }
}
