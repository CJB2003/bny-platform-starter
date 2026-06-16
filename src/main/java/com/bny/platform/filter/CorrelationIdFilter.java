package com.bny.platform.filter;

import com.bny.platform.config.BnyPlatformProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter extends OncePerRequestFilter {

    private final BnyPlatformProperties properties;

    public CorrelationIdFilter(BnyPlatformProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String correlationId;
            // 1. read header from request
            // 2. if null, generate UUID
            if (request.getHeader(properties.getCorrelationHeader()) != null) {
                correlationId = request.getHeader(properties.getCorrelationHeader());
            }
            else {
                correlationId = UUID.randomUUID().toString();
            }
            // 3. put in MDC
            MDC.put("correlationId", correlationId);
            // 4. add to response header
            response.setHeader(properties.getCorrelationHeader(), correlationId);
            // 5. call filterChain.doFilter()
            filterChain.doFilter(request, response);
        } finally {
            // 6. clear MDC
            MDC.clear();
        }
    }
}
