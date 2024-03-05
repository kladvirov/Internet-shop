package by.kladvirov.exception.handler;

import by.kladvirov.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Aspect
@Component
public class AopExceptionHandler {

    @Around("@annotation(by.kladvirov.exception.handler.annotation.MyPointcut)")
    public Object doRecoveryActions(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        String arguments = Arrays.toString(proceedingJoinPoint.getArgs());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try {
            return proceedingJoinPoint.proceed();
        } catch (ValidationException ex) {
            LogCall(methodName, arguments);
            return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        } catch (ServiceException ex) {
            LogCall(methodName, arguments);
            return buildErrorResponse(ex, ex.getHttpStatus(), request);
        } catch (Exception ex) {
            LogCall(methodName, arguments);
            return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        }
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), status.value(), LocalDateTime.now(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, status);
    }

    private void LogCall(String methodName, String arguments) {
        LOGGER.error("Caught exception in method: "
                + methodName + " with args: "
                + arguments + " the exception is: "
        );
    }

}
