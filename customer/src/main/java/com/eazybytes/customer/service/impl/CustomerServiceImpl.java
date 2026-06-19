package com.eazybytes.customer.service.impl;

import com.eazybytes.common.dto.MobileNumberUpdateDto;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.constants.CustomerConstants;
import com.eazybytes.customer.dto.CustomerDto;
import com.eazybytes.customer.entity.Customer;
import com.eazybytes.customer.exception.CustomerAlreadyExistsException;
import com.eazybytes.customer.exception.ResourceNotFoundException;
import com.eazybytes.customer.mapper.CustomerMapper;
import com.eazybytes.customer.repository.CustomerRepository;
import com.eazybytes.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private StreamBridge streamBridge;

    @Override
    public void createCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customer.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerUpdatedEvent.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerUpdatedEvent.getMobileNumber()));
        CustomerMapper.mapToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId)
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumberUpdateDto.getCurrentMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumberUpdateDto.getCurrentMobileNumber()));
        customer.setMobileNumber(mobileNumberUpdateDto.getNewMobileNumber());
        customerRepository.save(customer);
        updateAccountMobileNumber(mobileNumberUpdateDto);
        return true;
    }

    private void updateAccountMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        log.info("Sending updateAccountMobileNumber request for the details: {}", mobileNumberUpdateDto);
        boolean result = streamBridge.send("updateAccountMobileNumber-out-0", mobileNumberUpdateDto);
        log.info("Is the updateAccountMobileNumber request successfully triggered ? : {}", result);
    }

    @Override
    public boolean rollbackMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumberUpdateDto.getNewMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumberUpdateDto.getNewMobileNumber()));
        customer.setMobileNumber(mobileNumberUpdateDto.getCurrentMobileNumber());
        customerRepository.save(customer);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMobileNumberOrchest(String mobileNumber, String newMobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        customer.setMobileNumber(newMobileNumber);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean rollbackMobileNumberOrchest(String mobileNumber, String newMobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(newMobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", newMobileNumber));
        customer.setMobileNumber(mobileNumber);
        customerRepository.save(customer);
        return true;
    }

}
