package com.ishanitech.ipasal.ipasalwebservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandler implements WebMvcConfigurer {
	

    private String imageUploadingDirectory;
    
    
    public ResourceHandler(@Value("${com.ipasal.fileUploadingDirectory}") String imageUploadingDirectory) {
		this.imageUploadingDirectory = imageUploadingDirectory;
	}


	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Pictures/**")
        .addResourceLocations("file:" + imageUploadingDirectory);
    }
}