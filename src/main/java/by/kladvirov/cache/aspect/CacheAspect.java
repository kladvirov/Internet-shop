package by.kladvirov.cache.aspect;

import by.kladvirov.cache.Lfu;
import by.kladvirov.cache.annotation.CustomCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {

    private final Lfu lfu = new Lfu(20);

    @Around("execution(* by.kladvirov.service.*.findById(..))")
    public Object cachingFindById(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            if(lfu.containsKey(id)){
                return lfu.getElementFromCache(id);
            } else {
                Object retVal = joinPoint.proceed();
                lfu.putElementInCache(id, retVal);
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
            if(!lfu.containsKey(id)){
                lfu.putElementInCache(id, retVal);
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
            if(lfu.containsKey(id)){
                lfu.putElementInCache(id, retVal);
                return retVal;
            }
        }
        return joinPoint.proceed();
    }

    @Around("execution(* by.kladvirov.service.*.delete(..))")
    public void cachingDelete(ProceedingJoinPoint joinPoint) {
        boolean annotationPresent = joinPoint.getTarget().getClass().isAnnotationPresent(CustomCacheable.class);

        if(annotationPresent) {
            Long id = (Long) joinPoint.getArgs()[0];
            if(lfu.containsKey(id)) {
                lfu.delete(id);
            }
        }
    }

}
