package com.instantrip.was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WasApplication {

	public static void main(String[] args) {
		SpringApplication.run(WasApplication.class, args);
	}

}
