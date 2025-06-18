package com.kachina.recruitment_details_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class RecruitmentDetailsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentDetailsServiceApplication.class, args);
		log.info("\u001B[32mRecruitment Details Service is Ready.\u001B[0m");
	}

}
