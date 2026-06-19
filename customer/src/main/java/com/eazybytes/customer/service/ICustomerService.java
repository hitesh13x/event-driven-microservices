package com.eazybytes.customer.service;

import com.eazybytes.common.dto.MobileNumberUpdateDto;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.dto.CustomerDto;
import com.eazybytes.customer.entity.Customer;
import jakarta.validation.Valid;

public interface ICustomerService {

    /**
     * @param customerDto - CustomerDto Object
     */
    void createCustomer(Customer customer);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchCustomer(String mobileNumber);

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);

    /**
     * @param customerId - Input Customer ID
     * @return boolean indicating if the delete of Customer details is successful or not
     */
    boolean deleteCustomer(String customerId);

    boolean updateMobileNumber(@Valid MobileNumberUpdateDto mobileNumberUpdateDto);

    boolean rollbackMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto);

    boolean updateMobileNumberOrchest(String mobileNumber, String newMobileNumber);
    boolean rollbackMobileNumberOrchest(String mobileNumber, String newMobileNumber);
}
