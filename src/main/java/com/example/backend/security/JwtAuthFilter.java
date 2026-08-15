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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_COOKIE = "accessToken";

    private final JwtService jwtService;
    private final AccountService accountService;

    public JwtAuthFilter(
            JwtService jwtService,
            AccountService accountService) {

        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromCookie(request);
        /*
         * No authentication cookie.
         *
         * This does NOT automatically mean the request is unauthorized.
         * Spring Security may have public endpoints that don't require
         * authentication.
         */
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            /*
             * Avoid replacing an authentication that has already
             * been established by another authentication mechanism.
             */
            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                // Extract claims from JWT
                String email = jwtService.extractEmail(token);
                String role = jwtService.extractRole(token);
                String userId = jwtService.extractUserId(token);

                if (email == null || role == null || userId == null) {
                    unauthorized(response, "Invalid token");
                    return;
                }

                /*
                 * Verify that the account still exists.
                 */
                Optional<String> userOpt =
                        accountService.findAccountByEmail(email);

                if (userOpt.isEmpty()) {
                    unauthorized(response, "User account not found");
                    return;
                }

                String userEmail = userOpt.get();

                /*
                 * Validate JWT signature, subject and expiration.
                 */
                if (!jwtService.isTokenValid(token, userEmail)) {
                    unauthorized(response, "Invalid or expired token");
                    return;
                }

                /*
                 * Convert JWT role into a Spring Security authority.
                 *
                 * Example:
                 * ADMIN -> ROLE_ADMIN
                 */
                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + role));

                /*
                 * Create your application's authenticated user.
                 */
                AuthenticatedUser authenticatedUser =
                        new AuthenticatedUser(
                                userId,
                                userEmail,
                                role);

                /*
                 * Create Spring Security authentication.
                 */
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                authenticatedUser,
                                null,
                                authorities);

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                /*
                 * Tell Spring Security:
                 *
                 * "This request has been authenticated."
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }

            /*
             * Continue to the next filter/controller.
             */
            filterChain.doFilter(request, response);

        } catch (Exception e) {

            e.printStackTrace();
            unauthorized(
                    response,
                    "Invalid or expired authentication token");
        }
    }

    /**
     * Extracts the JWT from the HttpOnly authentication cookie.
     */
    private String extractTokenFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if (AUTH_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * Sends a 401 Unauthorized response.
     */
    private void unauthorized(
            HttpServletResponse response,
            String message) throws IOException {

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "%s"
                }
                """.formatted(message));
    }
}