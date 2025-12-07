package com.coursemate.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coursemate.dto.ApiResponse;

import java.io.IOException;

/**
 * Custom entry point for unauthorized access
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ApiResponse<?> apiResponse = new ApiResponse<>(false, "Unauthorized: " + authException.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
