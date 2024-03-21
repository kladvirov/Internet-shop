package by.kladvirov.cache.factory;

import by.kladvirov.cache.Cache;
import by.kladvirov.cache.impl.LFUCache;
import by.kladvirov.cache.impl.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CacheFactory {

    @Value("${application.cache.algorithm}")
    private String algorithm;

    @Value("${application.cache.size}")
    private Integer size;

    @Bean
    @ConditionalOnProperty("application.cache.algorithm")
    public Cache createCache(){
        if(algorithm.equalsIgnoreCase("LFU")) {
            return new LFUCache(size);
        } else if (algorithm.equalsIgnoreCase("LRU")) {
            return new LRUCache(size);
        }
        return null;
    }

}
