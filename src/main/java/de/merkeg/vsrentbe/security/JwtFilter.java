package de.merkeg.vsrentbe.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.merkeg.vsrentbe.exception.CustomErrorResponse;
import de.merkeg.vsrentbe.user.User;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;


@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        log.trace("Starting JWT filter procedure");
        String token = jwtTokenProvider.resolveToken(req);
        try{
            log.trace("Testing jwt token");
            if (token != null && jwtTokenProvider.validateToken(token)) {
                log.trace("User has valid jwt token in header");
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.trace("User has none or invalid jwt token");
            }
        } catch (JwtException e) {
            log.debug("User's jwt access token has expired");
            CustomErrorResponse err = CustomErrorResponse.builder()
                    .error(e.getClass().getName())
                    .message("JWT access token is expired")
                    .build();

            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write(convertObjectToJson(err));
        }
        log.trace("Finished JWT filter");
        filterChain.doFilter(req, res);
    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
