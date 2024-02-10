package com.aryan.loadbalancerclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LoadBalancerClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(LoadBalancerClientApplication.class)
				.web(WebApplicationType.NONE)
						.run(args);
		WebClient loadBalancedClient = ctx.getBean(WebClient.Builder.class).build();

		for (int i = 1; i< 10; i++) {
			String response = loadBalancedClient
					.get().uri("http://example-service/hello")
					.retrieve().toEntity(String.class)
					.block().getBody();
			System.out.println(response);
		}
	}

}
