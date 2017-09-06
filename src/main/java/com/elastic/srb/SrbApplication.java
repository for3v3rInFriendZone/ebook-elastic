package com.elastic.srb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableElasticsearchRepositories(basePackages = "com.elastic.srb.elasticRepository")
@EnableJpaRepositories(basePackages = "com.elastic.srb.repository")
public class SrbApplication {

	public static void main(String args[]) {
		SpringApplication.run(SrbApplication.class, args);
	}

}
