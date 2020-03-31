package com.github.wephotos.bughub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BughubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BughubApplication.class, args);
		log.info("================ Application Start Success ================");
	}

}
