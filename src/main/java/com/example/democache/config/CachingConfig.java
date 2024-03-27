package com.example.democache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CachingConfig {
}
