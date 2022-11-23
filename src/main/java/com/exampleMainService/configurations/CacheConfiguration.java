package com.exampleMainService.configurations;

import com.exampleMainService.utils.entities.CacheConfig;
import com.google.common.cache.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

@Configuration
//@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    CacheConfig cacheConfig = new CacheConfig();

    @Bean
    @Override
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {


            @Override
            protected Cache createConcurrentMapCache(final String name) {
                ConcurrentMap cache  = CacheBuilder.newBuilder()
                                .expireAfterWrite(cacheConfig.getDuration(), cacheConfig.getUnit())
                                .maximumSize(cacheConfig.getMaxSize()).recordStats().build().asMap();

                return new ConcurrentMapCache(name, cache, cacheConfig.isNullsAllowed());
            }
        };

        cacheManager.setCacheNames(Arrays.asList("departments","department","departmentById","employees","employee","employeeByDepartment"));
        return cacheManager;
    }
}