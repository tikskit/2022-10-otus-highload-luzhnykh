package ru.luzhnykh.socialnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SocialnetMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialnetMainApplication.class, args);
	}

}
