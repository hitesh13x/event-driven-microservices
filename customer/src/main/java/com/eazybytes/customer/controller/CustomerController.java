package com.eazybytes.customer.controller;

import com.eazybytes.common.dto.MobileNumberUpdateDto;
import com.eazybytes.customer.constants.CustomerConstants;
import com.eazybytes.customer.dto.ResponseDto;
import com.eazybytes.customer.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cust", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService iCustomerService;

    @PatchMapping("/mobile-number")
    public ResponseEntity<ResponseDto> updateMobileNumber(@Valid @RequestBody MobileNumberUpdateDto mobileNumberUpdateDto) {
        iCustomerService.updateMobileNumber(mobileNumberUpdateDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }
}
