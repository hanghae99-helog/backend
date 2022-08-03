package com.hanghae.helog.security;

import com.hanghae.helog.domain.User;
import com.hanghae.helog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String id = "";
        String token = "";

        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            id = jwtUtil.getUserIdFromToken(token);
        } else {
            super.doFilter(request, response, filterChain);

            return;
        }

        if(!id.equals("") && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.loadUserByUsername(id);

            log.info("JWT Filter token = {}", token);
            log.info("JWT Filter user = {}", user.getUsername());

            if(jwtUtil.isValidToken(token, user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        super.doFilter(request, response, filterChain);
    }
}
