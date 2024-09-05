package com.example.spring_boot.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
    @Component
    public class AuthenticationHandler {

        public void handleSuccessfulAuthentication(String login, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        }

        public void handleFailedAuthentication(String login, HttpServletRequest request, HttpServletResponse response) {
        }
    }

