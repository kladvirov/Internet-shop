package by.kladvirov.exception.handler;

import by.kladvirov.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AopExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopExceptionHandler.class);

    @Around("@annotation(org.springframework.web.bind.annotation.RestController)")
    public Object handleExceptionInRestController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        String arguments = Arrays.toString(proceedingJoinPoint.getArgs());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try {
            return proceedingJoinPoint.proceed();
        } catch (ValidationException ex) {
            logException(methodName, arguments, ex);
            return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        } catch (ServiceException ex) {
            logException(methodName, arguments, ex);
            return buildErrorResponse(ex, ex.getHttpStatus(), request);
        } catch (Exception ex) {
            logException(methodName, arguments, ex);
            return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        }
    }

    private void logException(String methodName, String arguments, Exception ex) {
        LOGGER.error("Caught exception in method: {} with args: {}. Exception: {}", methodName, arguments, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), status.value(), LocalDateTime.now(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, status);
    }
}
