package com.example.democache.config;


import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import com.example.democache.config.jackson.JacksonDeserializer;
import com.example.democache.config.jackson.JacksonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
public class SessionConfig implements BeanClassLoaderAware {

    private ClassLoader beanClassLoader;

    @Bean
    ConversionService springSessionConversionService() {
        var objectMapper = objectMapper();
        var conversionService = new GenericConversionService();
        conversionService.addConverter(new JacksonSerializer(objectMapper));
        conversionService.addConverter(new JacksonDeserializer(objectMapper));
        return conversionService;
    }

    private ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(this.beanClassLoader));
        return objectMapper;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

}

