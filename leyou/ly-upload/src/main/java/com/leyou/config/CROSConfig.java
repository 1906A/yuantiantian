package com.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CROSConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration cros=new CorsConfiguration();
        cros.addAllowedMethod("OPTIONS");
        cros.addAllowedMethod("POST");
        cros.addAllowedHeader("*");
        cros.addAllowedOrigin("http://manage.leyou.com");
        cros.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",cros);
        return new CorsFilter(source);
    }
}
