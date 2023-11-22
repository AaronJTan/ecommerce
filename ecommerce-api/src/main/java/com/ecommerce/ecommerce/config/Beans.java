package com.ecommerce.ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Beans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
