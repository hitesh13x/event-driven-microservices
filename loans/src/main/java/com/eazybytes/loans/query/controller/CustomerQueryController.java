package com.eazybytes.loans.query.controller;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.query.FindLoanQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CustomerQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam("mobileNumber") String mobileNumber) {
        FindLoanQuery findLoanQuery = new FindLoanQuery(mobileNumber);
        LoansDto loansDto = queryGateway.query(findLoanQuery, ResponseTypes.instanceOf(LoansDto.class)).join();
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }
}
