package com.eazybytes.customer;

import com.eazybytes.customer.command.interceptor.CustomerCommandInterceptor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Autowired
		public void registerCustomerCommandInterceptor(ApplicationContext context, CommandGateway commandGateway) {
		commandGateway.registerDispatchInterceptor(context.getBean(CustomerCommandInterceptor.class));
	}

}
