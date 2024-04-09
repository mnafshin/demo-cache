package com.example.democache.config.jackson;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeserializer implements GenericConverter {

    private final ObjectMapper objectMapper;

    public JacksonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(byte[].class, Object.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            return source == null ? null : objectMapper.readValue((byte[]) source, targetType.getType());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
