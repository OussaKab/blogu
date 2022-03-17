package dev.oussama.blogu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = "Unauthorized(401) => [" + request.getRemoteAddr() + "]:[" + request.getRemotePort() + "] -> " + authException.getLocalizedMessage();

        log.warn("session : [" + request.getSession().getId() + "], " + message);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized : " + request.getRemoteAddr() + ":" + request.getRemotePort() + " -> " + authException.getLocalizedMessage());
    }
}
