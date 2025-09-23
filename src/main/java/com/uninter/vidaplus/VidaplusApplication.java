package com.uninter.vidaplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.uninter.vidaplus")
public class VidaplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidaplusApplication.class, args);
	}

}
