package com.groww.chatbot.security;

import com.google.common.base.Strings;
import com.groww.chatbot.dto.GrowwUserDetails;
import com.groww.chatbot.service.UserServiceImpl;
import com.groww.chatbot.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * jwt filter class
 * to authorize incoming requests to secure endpoints
 * by validating their jwt auth token
 */

@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                                                                throws ServletException, IOException {
        // get authorization header
        final String authorizationHeader = request.getHeader("Authorization");
        // check for invalid format
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // get token
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            // extract email
            String email = jwtUtil.extractEmail(token);

            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // get user details
                GrowwUserDetails growwUserDetails = userService.loadUserByUsername(email);
                // validate token
                if(jwtUtil.validateToken(token, growwUserDetails)) {
                    // create username password auth token
                    // used by spring security for managing authentication
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(growwUserDetails, null,
                                                                growwUserDetails.getAuthorities());
                    // set token details from request
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                                       .buildDetails(request));
                    // set it into the context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (RuntimeException e) {
            log.error("Invalid token: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
