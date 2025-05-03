package com.devpuccino.accountservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(3)
public class LoggingFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            logRequest(requestWrapper);
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logResponse(responseWrapper,startTime);
        }

    }
    private void logRequest(ContentCachingRequestWrapper servletRequest) throws IOException {
        if (servletRequest instanceof HttpServletRequest) {

            StringBuilder logFormat = new StringBuilder("REQUEST [{}] url={} headers={}");
            StringBuilder optionalMessage = new StringBuilder();

            HttpServletRequest request = (HttpServletRequest) servletRequest;

            Map<String, String> headers = new HashMap<>();
            request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
                headers.put(headerName, request.getHeader(headerName));
            });

            Map<String, String> params = new HashMap<>();
            request.getParameterMap().keySet().stream().forEach(key -> {
                params.put(key, request.getParameter(key));
            });
            if (!params.isEmpty()) {
                optionalMessage.append(" parameter=");
                optionalMessage.append(objectMapper.writeValueAsString(params));
            }

            if (!params.isEmpty()) {
                logFormat.append("{}");
            }
            logger.info(logFormat.toString(),
                    request.getMethod(), request.getRequestURI(),
                    objectMapper.writeValueAsString(headers),
                    optionalMessage
            );
        }
    }
    private void logResponse(ContentCachingResponseWrapper response,Long startTime) throws IOException {
        Long processingTime = System.currentTimeMillis() - startTime;
        logger.info("RESPONSE [{} ms] status=[{}] body={}",processingTime,response.getStatus(),new String(response.getContentAsByteArray()));
        response.copyBodyToResponse();
    }

}
