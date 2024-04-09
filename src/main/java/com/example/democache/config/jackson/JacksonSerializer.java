package com.example.democache.config.jackson;

import java.io.UncheckedIOException;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializer implements GenericConverter {

    private final ObjectMapper objectMapper;

    public JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(Object.class, byte[].class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            return objectMapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
