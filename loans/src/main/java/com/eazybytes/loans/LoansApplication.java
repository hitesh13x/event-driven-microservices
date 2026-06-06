package com.eazybytes.loans;

import com.eazybytes.loans.command.interceptor.LoanCommandInterceptor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

	@Autowired
	public void registerLoanCommandInterceptor(ApplicationContext context, CommandGateway commandGateway) {
		commandGateway.registerDispatchInterceptor(context.getBean(LoanCommandInterceptor.class));
	}
}
