package com.devpuccino.accountservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(3)
public class ResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(ResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        chain.doFilter(requestWrapper, responseWrapper);
        logResponse(responseWrapper,startTime);
    }

    private void logResponse(ContentCachingResponseWrapper response,Long startTime) throws IOException {
        Long processingTime = System.currentTimeMillis() - startTime;
        logger.info("RESPONSE [{} ms] status=[{}] body={}",processingTime,response.getStatus(),new String(response.getContentAsByteArray()));
        response.copyBodyToResponse();
    }

}
