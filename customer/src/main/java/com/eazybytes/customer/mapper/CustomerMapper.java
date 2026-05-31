package com.eazybytes.customer.mapper;

import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.dto.CustomerDto;
import com.eazybytes.customer.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setActiveSw(customer.isActiveSw());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerUpdatedEvent customerUpdatedEvent, Customer customer) {
        customer.setCustomerId(customerUpdatedEvent.getCustomerId());
        customer.setName(customerUpdatedEvent.getName());
        customer.setEmail(customerUpdatedEvent.getEmail());
        customer.setMobileNumber(customerUpdatedEvent.getMobileNumber());
        if(customerUpdatedEvent.isActiveSw()) {
            customer.setActiveSw(true);
        }
        return customer;
    }

}
