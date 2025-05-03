package com.devpuccino.accountservice.filter;

import io.micrometer.tracing.BaggageInScope;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class TracingFilter extends OncePerRequestFilter {
    @Autowired
    private Tracer tracer;

    private static final String CONTEXT_CORRELATION_ID_NAME = "correlationId";
    private static final String HEADER_CORRELATION_ID_NAME = "correlationId";
    private static final String CORRELATION_ID_PREFIX = "ACCS-";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try (BaggageInScope scope = this.tracer.createBaggageInScope(CONTEXT_CORRELATION_ID_NAME, getCorrelationId(request))) {
            filterChain.doFilter(request, response);
        }
    }
    private String getCorrelationId(HttpServletRequest request){
        String correlationId = request.getHeader(HEADER_CORRELATION_ID_NAME);
        if(correlationId == null){
            correlationId = CORRELATION_ID_PREFIX+UUID.randomUUID().toString().replace("-","");
        }
        return correlationId;
    }
}
