package com.example.ECommerceBackendSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Bean
//public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//		// Allow anyone and anything access. Probably ok for Swagger spec
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin(“https://wiki.cors.net”);
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//}
@SpringBootApplication
public class ECommerceBackendSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackendSpringBootApplication.class, args);
	}

}
