package com.kachina.company_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dxj5ohl9j");
        config.put("api_key", "789545947643535");
        config.put("api_secret", "twsxl3lOKUFroBVQvZr4eE0iNdQ");
        return new Cloudinary(config);
    }

}
