package by.kladvirov.cache.aspect;

import by.kladvirov.cache.Cache;
import by.kladvirov.cache.annotation.CustomCacheable;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AllArgsConstructor
public class CacheAspect {

    private Cache cache;

    @Around("execution(* by.kladvirov.service.*.findById(..))")
    public Object cachingFindById(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            if(cache.containsKey(id)){
                return cache.getElementFromCache(id);
            } else {
                Object retVal = joinPoint.proceed();
                cache.putElementInCache(id, retVal);
                return retVal;
            }
        }
        return joinPoint.proceed();
    }

    @Around("execution(* by.kladvirov.service.*.save(..))")
    public Object cachingSave(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            Object retVal = joinPoint.proceed();
            if(!cache.containsKey(id)){
                cache.putElementInCache(id, retVal);
                return retVal;
            }
        }
        return joinPoint.proceed();
    }

    @Around("execution(* by.kladvirov.service.*.update(..))")
    public Object cachingUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            Object retVal = joinPoint.proceed();
            if(cache.containsKey(id)){
                cache.putElementInCache(id, retVal);
                return retVal;
            }
        }
        return joinPoint.proceed();
    }

    @Around("execution(* by.kladvirov.service.*.delete(..))")
    public void cachingDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            if(cache.containsKey(id)) {
                cache.delete(id);
            }
        }
        joinPoint.proceed();
    }

}
