package com.example.ourivesaria;

import com.example.ourivesaria.configs.RSAKeyRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class ProfileSpringApplication implements CommandLineRunner {

	@Value("${info}")
	private String environment;

	public static void main(String[] args) {

		SpringApplication.run(ProfileSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(environment);
	}
}
