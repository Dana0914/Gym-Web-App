package epam.com.gymapplication.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("TransactionId: {}, Request: {}, Method: {}, Headers: {}, URL: {} ", transactionId,
                    httpRequest.getRequestURI(),
                    httpRequest.getMethod(),
                    httpRequest.getHeaderNames(),
                    httpRequest.getRequestURL());

            chain.doFilter(request, response);

            logger.info("TransactionId: {} ResponseStatus: {}", transactionId,
                    httpResponse.getStatus());

        } catch (Exception e) {
            logger.error("TransactionId: {}, Error occurred: {}", transactionId, e.getMessage());
        } finally {
            MDC.clear();
        }


    }

}
