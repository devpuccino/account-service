package com.devpuccino.accountservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(2)
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = LogManager.getLogger(RequestLoggingFilter.class);

    public RequestLoggingFilter() {
        this.setIncludePayload(true);

    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        try {
            this.logRequest(request);
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper contentCachingRequestWrapper = (ContentCachingRequestWrapper) request;
            String requestBody = contentCachingRequestWrapper.getContentAsString();
            if(!requestBody.isEmpty()){
                try {
                    Map<String, Object> requestBodyMap = objectMapper.readValue(requestBody, Map.class);
                    logger.info("REQUEST body={}",objectMapper.writeValueAsString(requestBodyMap));
                } catch (JsonProcessingException e) {
                    if(logger.isDebugEnabled()) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void logRequest(ServletRequest servletRequest) throws IOException {
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
}
