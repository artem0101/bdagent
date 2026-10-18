package ru.education.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        var module = new JavaTimeModule();
        module.addSerializer(java.time.Instant.class, 
            com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer.INSTANCE);
        mapper.registerModule(module);
        return mapper;
    }
}
