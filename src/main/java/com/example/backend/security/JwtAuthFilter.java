package com.example.backend.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.model.AuthenticatedUser;
import com.example.backend.service.AccountService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountService accountService;

    public JwtAuthFilter(JwtService jwtService,
            AccountService accountService) {
        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // No token → continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            // Extract JWT claims
            String email = jwtService.extractEmail(token);
            String role = jwtService.extractRole(token);
            String userId = jwtService.extractUserId(token);
            
            // Avoid re-authenticating existing context
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Optional DB verification
                Optional<String> userOpt = accountService.findAccountByEmail(email);

                if (userOpt.isEmpty()) {
                    unauthorized(response, "User account not found");
                    return;
                }

                String userEmail = userOpt.get();

                // Validate JWT
                if (!jwtService.isTokenValid(token, userEmail)) {
                    unauthorized(response, "Invalid token");
                    return;
                }

                // Spring Security authorities
                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + role));

                // Authenticated principal
                AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                        userId,
                        userEmail,
                        role);

                // Authentication object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authenticatedUser,
                        null,
                        authorities);

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                // Attach authenticated user to Spring Security context
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            unauthorized(response, "Invalid or expired token");
        }
    }

    private void unauthorized(HttpServletResponse response,
            String message) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write("""
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "%s"
                }
                """.formatted(message));
    }
}