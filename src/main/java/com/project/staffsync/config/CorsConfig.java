package com.project.staffsync.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;
    
    @Bean
    public FilterRegistrationBean<?> corsFilter() {
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	CorsConfiguration config = new CorsConfiguration();
    	config.setAllowCredentials(true);
    	List<String> allowedOrigins = new ArrayList<String>();
    	allowedOrigins.add("http://localhost:5173");
    	config.setAllowedOrigins(allowedOrigins);
    	config.setAllowedHeaders(Arrays.asList(
    			HttpHeaders.AUTHORIZATION,
    			HttpHeaders.CONTENT_TYPE,
    			HttpHeaders.ACCEPT
    			));
    	config.setAllowedMethods(Arrays.asList(
    			HttpMethod.GET.name(),
    			HttpMethod.POST.name(),
    			HttpMethod.PUT.name(),
    			HttpMethod.DELETE.name()
    			));
    	config.setMaxAge(MAX_AGE);
    	source.registerCorsConfiguration("/**", config);
    	
    	FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    	
    	bean.setOrder(CORS_FILTER_ORDER);
    	
    	return bean;
    }
	
}


