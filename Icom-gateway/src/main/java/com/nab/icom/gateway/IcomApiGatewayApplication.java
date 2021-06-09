package com.nab.icom.gateway;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients

public class IcomApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcomApiGatewayApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// config.setAllowedOrigins(Collections.singletonList("http://52.148.92.86:8080"));
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	// @Bean
    // public WebMvcConfigurer corsConfigurer() {
    //     return new WebMvcConfigurer() {

    //         public void addCorsMappings(CorsRegistry registry) {
    //             registry.addMapping("/books/**")
    //                     .allowedOrigins("http://localhost:4200")
    //                     .allowedMethods("GET", "POST","HEAD","PUT","DELETE","PATCH","OPTIONS");
    //         }

	// 		@Override
	// 		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void addFormatters(FormatterRegistry arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void addInterceptors(InterceptorRegistry arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void addResourceHandlers(ResourceHandlerRegistry arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void addViewControllers(ViewControllerRegistry arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configurePathMatch(PathMatchConfigurer arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void configureViewResolvers(ViewResolverRegistry arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
	// 			// TODO Auto-generated method stub
				
	// 		}

	// 		@Override
	// 		public Validator getValidator() {
	// 			// TODO Auto-generated method stub
	// 			return null;
	// 		}

	// 		@Override
	// 		public MessageCodesResolver getMessageCodesResolver() {
	// 			// TODO Auto-generated method stub
	// 			return null;
	// 		}
    //     };
   // }
}

