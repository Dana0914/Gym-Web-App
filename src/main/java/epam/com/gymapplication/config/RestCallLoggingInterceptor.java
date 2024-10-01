package epam.com.gymapplication.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RestCallLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RestCallLoggingInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String transactionId = UUID.randomUUID().toString();
        logger.info("TransactionId: {}, Incoming Request: {}, Method: {}, URL: {}",
                transactionId, request.getRequestURI(), request.getMethod(), request.getRequestURL());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String transactionId = UUID.randomUUID().toString();
        logger.info("TransactionId: {}, ResponseStatus: {}, URI: {} ", transactionId, response.getStatus(),
                request.getRequestURI());

        if (ex != null) {
            logger.error("TransactionId: {}, Error occurred: {}", transactionId, ex.getMessage());
        }
    }
}
